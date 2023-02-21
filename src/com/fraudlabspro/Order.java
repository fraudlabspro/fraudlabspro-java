package com.fraudlabspro;

import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.Formatter;

/**
 * FraudLabsPro Order module.
 * Validates order for possible fraud and feedback user decision.
 */

public class Order{
    /**
     * Order statuses.
     *
     * @const string
     */
    public final String APPROVE = "APPROVE";
    public final String REJECT = "REJECT";
    public final String REJECT_BLACKLIST = "REJECT_BLACKLIST";

    /**
     * Payment methods.
     *
     * @const string
     */
    public final String CREDIT_CARD = "CREDITCARD";
    public final String PAYPAL = "PAYPAL";
    public final String GOOGLE_CHECKOUT = "GOOGLECHECKOUT";
    public final String CASH_ON_DELIVERY = "COD";
    public final String MONEY_ORDER = "MONEYORDER";
    public final String WIRE_TRANSFER = "WIRED";
    public final String BANK_DEPOSIT = "BANKDEPOSIT";
    public final String BITCOIN = "BITCOIN";
    public final String OTHERS = "OTHERS";

    /**
     * ID types.
     *
     * @const string
     */
    public final String FLP_ID = "fraudlabspro_id";
	public final String ORDER_ID = "user_order_id";

    /** Screen Order API
     * Screen an order transaction for payment fraud.
     * This REST API will detects all possibles fraud traits based on the input parameters supplied.
     * The more input parameter supplied, the higher accuracy of fraud detection.
     * @param data
     *         Parameters that required to screen orders
     * @return string
     *          Returns processed orders in JSON || XML format
     */
    public String validate(Hashtable<String, String> data) {
        String flp_checksum = "";

        try {
            if (data.get("email") != null && !data.get("email").isEmpty()) {
                data.put("email_domain", data.get("email").substring(data.get("email").indexOf("@") + 1));  // gets the email domain
                data.put("email_hash", doHash(data.get("email")));  // generates email hash
            }

            if (data.get("username") != null && !data.get("username").isEmpty()) {
                data.put("username_hash", doHash(data.get("username")));  // generates username hash
                data.remove("username");
            }

            if (data.get("password") != null && !data.get("password").isEmpty()) {
                data.put("password_hash", doHash(data.get("password"))); // generates password hash
                data.remove("password");
            }

            if (data.get("user_phone") != null && !data.get("user_phone").isEmpty()) {
                data.put("user_phone", data.get("user_phone").replaceAll("[^a-zA-Z0-9]", ""));
            }

            if (data.get("currency") == null || data.get("currency").isEmpty()) {
                data.put("currency", "USD");  //  default currency is USD
            }

            if (data.get("number") != null && !data.get("number").isEmpty()) {
                data.put("bin_no", data.get("number").substring(0, 6));
                data.put("card_hash", doHash(data.get("number")));  // generates card hash
            }

            if (data.get("avs") != null && !data.get("avs").isEmpty()) {
                data.put("avs_result", doHash(data.get("avs")));
                data.remove("avs");
            }

            if (data.get("cvv") != null && !data.get("cvv").isEmpty()) {
                data.put("cvv_result", doHash(data.get("cvv")));
                data.remove("cvv");
            }

            if (data.get("amount") != null && !data.get("amount").isEmpty()) {
                data.put("amount", new DecimalFormat("#.00").format(Double.parseDouble(data.get("amount"))));
            }

            StringBuilder dataStr = new StringBuilder();
            data.put("source", "sdk-java");
            data.put("source_version", FraudLabsPro.VERSION);
            data.put("flp_checksum", flp_checksum);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                dataStr.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            
            String post = "key=" + FraudLabsPro.APIKEY + dataStr;

            return Http.post(new URL("https://api.fraudlabspro.com/v1/order/screen"), post);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Feedback Order API
     * Update status of a transaction from pending-manual-review to APPROVE, REJECT or IGNORE.
     * The FraudLabs Pro algorithm will improve the formula in determine the FraudLabs Pro score using the data collected.
     * @param data
     *         Parameters that required to set feedback
     * @return string
     *          Returns order feedback in JSON || XML format
     */
    public String feedback(Hashtable<String, String> data) {
        try {
            StringBuilder dataStr = new StringBuilder();
            data.put("source", "sdk-java");
            data.put("source_version", FraudLabsPro.VERSION);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                dataStr.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String post = "key=" + FraudLabsPro.APIKEY + dataStr;

            return Http.post(new URL("https://api.fraudlabspro.com/v1/order/feedback"), post);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Get Order Result API
     * Retrieve an existing transaction from FraudLabs Pro fraud detection system.
     * @param data
     *         Parameters that required to get order results
     * @return string
     *          Returns order results in JSON || XML format
     */
    public String getTransaction(Hashtable<String, String> data) {
        try {
            StringBuilder dataStr = new StringBuilder();
            data.put("source", "sdk-java");
            data.put("source_version", FraudLabsPro.VERSION);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                dataStr.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return Http.get(new URL("https://api.fraudlabspro.com/v1/order/result?key=" + FraudLabsPro.APIKEY + dataStr));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** To hash a string to protect its real value
     *
     * @param value
     *         Value to be hashed
     * @return
     *         Returns hashed string value
     */
    private String doHash(String value) {
        String hash = "fraudlabspro_" + value;
        for (int i = 0; i < 65536; ++i) {
            hash = SHA1("fraudlabspro_" + hash);
        }
        return hash;
    }


    private String SHA1(String s) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            Formatter formatter = new Formatter();
            sha1.update(s.getBytes());

            byte[] hash = sha1.digest();
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
