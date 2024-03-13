# Quickstart

## Dependencies

This module requires API key to function. You may subscribe a free API key at [https://www.fraudlabspro.com](https://www.fraudlabspro.com)

## Installation

To use this module in your Java project, please visit [https://central.sonatype.com/artifact/com.fraudlabspro/fraudlabspro-java](https://central.sonatype.com/artifact/com.fraudlabspro/fraudlabspro-java) to copy the corrensponding code to your building tool.

## Sample Codes

### Validate Order

You can validate your order as below:

```java
import com.fraudlabspro.*;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        // Configures FraudLabs Pro API key
        FraudLabsPro.APIKEY = "YOUR_API_KEY";

        // Screen Order API
        Order order = new Order();

        // Sets order details
        Hashtable<String, String> data = new Hashtable<>();

        data.put("ip", "146.112.62.105");  // IP parameter is mandatory
        data.put("first_name", "Hector");
        data.put("last_name", "Henderson");
        data.put("email", "hh5566@gmail.com");
        data.put("user_phone", "561-628-8674");

        // Billing information
        data.put("bill_addr", "1766 PowderHouse Road");
        data.put("bill_city", "West Palm Beach");
        data.put("bill_state", "FL");
        data.put("bill_country", "US");
        data.put("bill_zip_code", "33401");
        data.put("number", "4556553172971283");

        // Order information
        data.put("user_order_id", "67398");
        data.put("user_order_memo", "Online shop");
        data.put("amount", "79.89");
        data.put("quantity", "1");
        data.put("currency", "USD");
        data.put("payment_mode", order.CREDIT_CARD);  // Please refer reference section for full list of payment methods

        // Shipping information
        data.put("ship_addr", "4469 Chestnut Street");
        data.put("ship_city", "Tampa");
        data.put("ship_state", "FL");
        data.put("ship_zip_code", "33602");
        data.put("ship_country", "US");

        String result = order.validate(data);  // Sends order details to FraudLabs Pro
    }
}
```

### Get Transaction

You can get the details of a transaction as below:

```java
import com.fraudlabspro.*;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        // Configures FraudLabs Pro API key
        FraudLabsPro.APIKEY = "YOUR_API_KEY";

        // Get Order Result API
        Order orderResults = new Order();

        // Sets order ID to return all available information regarding the order
        Hashtable<String, String> data = new Hashtable<>();
        data.put("id", "20180709-NHAEUK");
        data.put("id_type", orderResults.FLP_ID);

        String result = orderResults.getTransaction(data);  // Obtains order results from FraudLabs Pro
    }
}
```

### Feedback

You can approve, reject or ignore a transaction as below:

```java
import com.fraudlabspro.*;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        // Configures FraudLabs Pro API key
        FraudLabsPro.APIKEY = "YOUR_API_KEY";

        // Feedback Order API
        Order fb = new Order();

        // Sets feedback details
        Hashtable<String, String> data = new Hashtable<>();
        data.put("id", "20180709-NHAEUK");
        data.put("action", fb.APPROVE);  // Please refer to reference section for full list of feedback statuses
        data.put("note", "This customer made a valid purchase before.");

        String result = fb.feedback(data);  // Sends feedback details to FraudLabs Pro
    }
}
```

### Send SMS Verification

You can send SMS verification for authentication purpose as below:

```java
import com.fraudlabspro.*;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        // Configures FraudLabs Pro API key
        FraudLabsPro.APIKEY = "YOUR_API_KEY";
        
        // Send SMS Verification API
        SMSVerification sms = new SMSVerification();

        // Sets SMS details for authentication purpose
        Hashtable<String, String> data = new Hashtable<>();
        data.put("tel", "+123456789");
        data.put("country_code", "US");
        data.put("mesg", "Hi, your OTP is <otp>.");
        data.put("otp_timeout", 3600);

        String result = sms.sendSMS(data);
    }
}
```

### Get SMS Verification Result

You can verify the OTP sent by Fraudlabs Pro SMS verification API as below:

```java
import com.fraudlabspro.*;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        // Configures FraudLabs Pro API key
        FraudLabsPro.APIKEY = "YOUR_API_KEY";

        // Get Verification Result API
        SMSVerification verification = new SMSVerification();

        // Sets transaction ID and otp details for verification purpose
        Hashtable<String, String> data = new Hashtable<>();
        data.put("tran_id", "UNIQUE_TRANS_ID");
        data.put("otp", "OTP_RECEIVED");

        String result = verification.verifySMS(data);
    }
}
```