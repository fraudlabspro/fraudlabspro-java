package com.fraudlabspro;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;

class SMSVerification {
    /** Send SMS Verification API
     * Send an SMS with verification code and a custom message for authentication purpose.
     * @param data
     *         Parameters that required to send SMS verification
     * @return string
     *          Returns SMS verification results in JSON || XML format
     */
    public String sendSMS(Hashtable<String, String> data) {
        try {
            StringBuilder dataStr = new StringBuilder();
            data.put("source", "FraudLabsPro JAVA SDK");
            data.put("source_version", FraudLabsPro.VERSION);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                dataStr.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String post = "key=" + FraudLabsPro.APIKEY + dataStr;

            return Http.post(new URL("https://api.fraudlabspro.com/v1/verification/send"), post);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Get Verification Result API
     * Verify that an OTP sent by the Send SMS Verification API is valid.
     * @param data
     *         Parameters that required to get SMS verification results
     * @return string
     *          Returns sms verification results in JSON || XML format
     */
    public String verifySMS(Hashtable<String, String> data) {
        try {
            StringBuilder dataStr = new StringBuilder();
            data.put("source", "FraudLabsPro JAVA SDK");
            data.put("source_version", FraudLabsPro.VERSION);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                dataStr.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return Http.get(new URL("https://api.fraudlabspro.com/v1/verification/result?key=" + FraudLabsPro.APIKEY + dataStr));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
