FraudLabs Pro Java SDK
========================
This Java module enables user to easily implement fraud detection feature into their solution using the API from https://www.fraudlabspro.com.

Below are the features of this Java module:
- Fraud analysis and scoring
- IP address geolocation & proxy validation
- Email address validation
- Credit card issuing bank validation
- Transaction velocity validation
- Device transaction validation
- Blacklist validation
- Custom rules trigger
- Email notification of fraud orders
- Mobile app notification of fraud orders

This module requires API key to function. You may subscribe a free API key at https://www.fraudlabspro.com




Usage Example
============
### Validate Order

#### Object Properties

| Property Name                     | Property Type | Description                                                  |
| --------------------------------- | ------------- | ------------------------------------------------------------ |
| ip                                | string        | IP address of online transaction. It supports both IPv4 and IPv6 address format. |
| firstName     | string        | User's first name.                                           |
| lastName      | string        | User's last name.                                            |
| username      | string        | User's username.                                             |
| password      | string        | User's password.                                             |
| email         | string        | User's email address.                                        |
| user_phone    | string        | User's phone number.                                         |
| address       | string        | Street address of billing address.                           |
| city          | string        | City of billing address.                                     |
| state         | string        | State of billing address. It supports state codes, e.g. NY (New York), for state or province of United States or Canada. Please refer to [State & Province Codes](https://www.fraudlabspro.com/developer/reference/state-and-province-codes) for complete list. |
| postcode      | string        | Postal or ZIP code of billing address.                       |
| country       | string        | Country of billing address. It requires the input of ISO-3166 alpha-2 country code, e.g. US for United States. Please refer to [Country Codes](https://www.fraudlabspro.com/developer/reference/country-codes) for complete list. |
| orderId       | string        | Merchant identifier to uniquely identify a transaction. It supports maximum of 15 characters user order id input. |
| note          | string        | Merchant description of an order transaction. It supports maximum of 200 characters. |
| amount        | float         | Amount of the transaction.                                   |
| quantity      | integer       | Total quantity of the transaction.                           |
| currency      | string        | Currency code used in the transaction. It requires the input of ISO-4217 (3 characters) currency code, e.g. USD for US Dollar. Please refer to [Currency Codes](https://www.fraudlabspro.com/developer/reference/currency-codes) for complete list. |
| department    | string        | Merchant identifier to uniquely identify a product or service department. |
| paymentMethod | string        | Payment mode of transaction. Please see [reference section](#payment-method).  |
| number        | string        | Billing credit card number or BIN number.                    |
| avs           | string        | The single character AVS result returned by the credit card processor. Please refer to [AVS & CVV2 Response Codes](https://www.fraudlabspro.com/developer/reference/avs-and-cvv2-response-codes) for details. |
| cvv           | string        | The single character CVV2 result returned by the credit card processor. Please refer to [AVS & CVV2 Response Codes](https://www.fraudlabspro.com/developer/reference/avs-and-cvv2-response-codes) for details. |
| address       | string        | Street address of shipping address.                          |
| city          | string        | City of shipping address.                                    |
| state         | string        | State of shipping address. It supports state codes, e.g. NY - New York, for state or province of United States or Canada. Please refer to [State & Province Codes](https://www.fraudlabspro.com/developer/reference/state-and-province-codes) for complete list. |
| postcode      | string        | Postal or ZIP code of shipping address.                      |
| country       | string        | Country of shipping address. It requires the input of ISO-3166 alpha-2 country code, e.g. US for United States. Please refer to [Country Codes](https://www.fraudlabspro.com/developer/reference/country-codes) for complete list. |

```
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

#### Parameter Properties

| Parameter Name | Parameter Type | Description                                                  |
| -------------- | -------------- | ------------------------------------------------------------ |
| id             | string         | FraudLabs Pro transaction ID or Order ID.                    |
| type           | string         | ID type. Either: **[objectOfOrder].FLP_ID** or **[objectOfOrder].ORDER_ID** |

```
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

#### Object Properties

| Property Name | Property Type | Description                                                  |
| ------------- | ------------- | ------------------------------------------------------------ |
| id            | string        | Unique transaction ID generated from **Validate** function.  |
| status        | string        | Perform APPROVE, REJECT, or REJECT_BLACKLIST action to transaction.    Refer to [reference section](#feedback-status) for status code. |
| note          | string        | Notes for the feedback request.                              |

```
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
        data.put("notes", "This customer made a valid purchase before.");

        String result = fb.feedback(data);  // Sends feedback details to FraudLabs Pro
    }
}
```



## SMS Verification

### Send SMS Verification

#### Object Properties

| Property Name | Property Type | Description                                                  |
| ------------- | ------------- | ------------------------------------------------------------ |
| tel           | string        | The recipient mobile phone number in E164 format which is a plus followed by just numbers with no spaces or parentheses. |
| mesg          | string        | The message template for the SMS. Add &lt;otp&gt; as placeholder for the actual OTP to be generated. Max length is 140 characters. |
| country_code  | string        | ISO 3166 country code for the recipient mobile phone number. If parameter is supplied, then some basic telephone number validation is done. |

```
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
        data.put("tel", "+15616288674");
        data.put("country_code", "US");
        data.put("mesg", "Hi, your OTP is <otp>.");

        String result = sms.sendSMS(data);
    }
}
```



### Get SMS Verification Result

#### Object Properties

| Property Name | Property Type | Description                                                  |
| ------------- | ------------- | ------------------------------------------------------------ |
| tran_id       | string        | The unique ID that was returned by the Send SMS Verification that triggered the OTP sms. |
| otp           | string        | The OTP that was sent to the recipient’s phone. |

```
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



# Reference

#### Payment Method

| Payment Method                       |
| ------------------------------------ |
| [objectOfOrder].CREDIT_CARD          |
| [objectOfOrder].PAYPAL               |
| [objectOfOrder].GOOGLE_CHECKOUT      |
| [objectOfOrder].CASH_ON_DELIVERY     |
| [objectOfOrder].MONEY_ORDER          |
| [objectOfOrder].WIRE_TRANSFER        |
| [objectOfOrder].BANK_DEPOSIT         |
| [objectOfOrder].BITCOIN              |
| [objectOfOrder].OTHERS               |



#### Feedback Status

| Feedback Status                      | Description                                 |
| ------------------------------------ | ------------------------------------------- |
| [objectOfOrder].APPROVE              | Approves an order that under review status. |
| [objectOfOrder].REJECT               | Rejects an order than under review status.  |
| [objectOfOrder].REJECT_BLACKLIST     | Rejects and blacklists an order.            |




LICENCE
=====================
See the LICENSE file.
