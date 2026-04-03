package com.fraudlabspro;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;

class Payment {
    /** Payment Feedback API
     * Report the final payment status back to the system, helping improve fraud detection and risk assessment.
     * @param data
     *         Parameters that required to send payment feedback
     * @return string
     *          Returns results in JSON || XML format
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

            return Http.post(new URL("https://api.fraudlabspro.com/v2/payment/feedback"), post);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
