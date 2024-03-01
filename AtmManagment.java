import java.util.*;

public class AtmManagement {
    // not implemented any receipt printing method it will be developed in future
    static Map<String,String> accountHolderData=new HashMap<>()

    {{
        accountHolderData.put("Name","Ramesh");
        accountHolderData.put("Password","1234");
        accountHolderData.put("CardNumber","CARD123456");
        accountHolderData.put("ExpireMonth","6");
        accountHolderData.put("ExpireYear","2026");
        accountHolderData.put("Balance","1200.00");
    }};

    static Map<String,Map<String,String>> accountHolderDetails=new HashMap<>(){{
        accountHolderDetails.put("CARD123456",accountHolderData);
    }};
    // used to store the transactionDetails hear
    static Map<String, List<String>> accountHolderTransaction=new HashMap<>();

    // Main Method starts hear
    public static void main(String[] args)
    {
        System.out.println("===================Welcome PeopleBank ATM===================");
        System.out.println("Enter thr card number to verify the card");
        Scanner input = new Scanner(System.in);
        String cardNumber = input.next();
        System.out.println("enter the expireMonth in the card");
        int expireMonth = input.nextInt();
        System.out.println("enter the expireYear in the card");
        int expireYear = input.nextInt();
        System.out.println("Choose the cardType");
        String cardType="" ;
        System.out.println("1.VISA 2.RuPay 3.Contactless 4.Other");
        int choice = input.nextInt();
        // choice for choose the ATM cardType
        switch (choice) {
            case 1:
                cardType = "VISA";
                break;
            case 2:
                cardType = "RuPay";
                break;
            case 3:
                cardType = "Contactless";
                break;
            case 4:
                //If the User don't have cardType given in Option he can choose Other Option to add the cardType
                System.out.println("enter the cardType have in your card");
                String otherCardType = input.next();
                String[]CardTypes = {"VISA", "RuPay", "Contactless", "MasterCard"};
                if (Arrays.asList(CardTypes).contains(otherCardType)) {
                    cardType = otherCardType;

                } else {

                    System.out.println("Very Sorry,we are not Accept this type of Card");
                    System.out.println("we are Accepting only VISA,RuPay,Contactless and MasterCard");
                    main(null);
                }
                break;
        }
        AtmManagement atmManagement=new AtmManagement();
        boolean verifiedCard= atmManagement.verifyCard(cardNumber,expireMonth,expireYear,accountHolderDetails);

        //verifying the card details
        if(verifiedCard)
        {
            //when the card is verified then entered to verify pin
            System.out.println("ATM card is verified");

            boolean verifiedPinNumber=false;
            int remainingAttempts=3;
            // number of trails to enter the correct pin_Number
            while (!verifiedPinNumber && remainingAttempts > 0){
                verifiedPinNumber =atmManagement.verifyPinNumber(accountHolderDetails, cardNumber);
                remainingAttempts--;

                if (!verifiedPinNumber) {
                    System.out.printf("Incorrect PIN. Please try again. You have %d attempts remaining.\n", remainingAttempts);
                }
            }
            // verifying the pin_number
            if (verifiedPinNumber) {
                System.out.println("Pin is verified!");
                atmManagement.menuOption(accountHolderDetails,cardNumber);

            } else {
                System.out.println("PIN limit exceeded. Your card is locked. Please contact your bank.");
            }

        }else {
            System.out.println("try again ");
            main(null);
        }
    }
    // used to add the remaining amount after the withdrew amount
    private static void putRemainingBalance(Map<String,Map<String,String>> accountHolderDetails,String cardNumber,int totalAmount)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        accountHolderData.replace("Balance",Integer.toString(totalAmount));

    }
    // Replace the old password with new password using this method
    private static String replaceOldWithNewPassword(Map<String,Map<String,String>> accountHolderDetails,String cardNumber,int newPinNumber)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        accountHolderData.replace("Password",Integer.toString(newPinNumber));
        return "Successfully change the pin your new pin is "+ getCardPassword(accountHolderDetails,cardNumber);
    }

    //Used to get the Card Balance using this method
    private static String getCardBalance(Map<String,Map<String,String>> accountHolderDetails,String cardNumber)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        return accountHolderData.get("Balance");
    }
    //Used to get the Card Number using this method
    private static String getCardNumber(Map<String,Map<String,String>> accountHolderDetails,String cardNumber)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        return accountHolderData.get("CardNumber");
    }
    //Used to get the Card userName using this method
    private static String getCardUserName(Map<String,Map<String,String>> accountHolderDetails,String cardNumber)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        return accountHolderData.get("Name");
    }
    //Used to get the Card Password using this method
    private static String getCardPassword(Map<String,Map<String,String>> accountHolderDetails, String cardNumber)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        return accountHolderData.get("Password");
    }
    //Used to get the Card expire month using this method
    private static String getCardExpireMonth(Map<String,Map<String,String>> accountHolderDetails, String cardNumber)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        return accountHolderData.get("ExpireMonth");
    }
    //Used to get the Card expire year using this method
    private static String getCardExpireYear(Map<String,Map<String,String>> accountHolderDetails, String cardNumber)
    {
        Map<String,String> accountHolderData=accountHolderDetails.get(cardNumber);
        return accountHolderData.get("ExpireYear");
    }


    // This method verify the card details to access the menu option
    public boolean verifyCard(String cardNumber,int expireMonth,int expireYear,Map<String,Map<String,String>> accountHolderDetails)

    {

        if (cardNumber.length() == 10) {

            if (cardNumber.equals(getCardNumber(accountHolderDetails, cardNumber)))
            {
                if(Integer.toString(expireMonth).equals(getCardExpireMonth(accountHolderDetails,cardNumber)) && Integer.toString(expireYear).equals(getCardExpireYear(accountHolderDetails,cardNumber)))
                {
                    System.out.println("This is a valid CardNumber");
                    return true;
                }else{
                    System.out.println("please enter the valid expireMonth and expireyear");
                    return false;
                }
            } else {
                System.out.println("!This is not valid CardNumber");
                return false;
            }

        } else {
            System.out.println("This is a invalid CardNumber");
            return false;
        }
    }

    //This method verify the pin_number to access the menu option
    public boolean verifyPinNumber(Map<String,Map<String,String>> accountHolderDetails,String cardNumber)
    {
        Scanner inputPin=new Scanner(System.in);
        System.out.println("Enter thr Pin number to verify");
        int pinNumber=inputPin.nextInt();
        String pinNumberToString=Integer.toString(pinNumber);
        return pinNumberToString.length() == 4 && pinNumberToString.equals(getCardPassword(accountHolderDetails, cardNumber));
    }
    // menuOption  to choose the language and also transaction option
    public void menuOption(Map<String,Map<String,String>> accountHolderDetails,String cardNumber)
    {
        //Selection of Language
        Scanner input=new Scanner(System.in);
        String languageSelection="";
        System.out.println("Select you language");
        System.out.println("1.kannada 2.Hindi 3.Telugu 4.Tamil");
        int choice=input.nextInt();
        switch(choice)
        {
            case 1:languageSelection="Kannada";
                break;
            case 2: languageSelection="Hindi";
                break;
            case 3:languageSelection="Telugu";
                break;
            case 4:languageSelection="Tamil";
                break;
            default: languageSelection="English";
        }

        //Display the menuOption hear
        System.out.println("WELCOME"+" "+getCardUserName(accountHolderDetails,cardNumber));
        while(true)
        {
            List<String> transactionDetails=new ArrayList<>();
            AtmManagement atmManagement=new AtmManagement();
            System.out.println("Choose you Transaction Option");
            System.out.println("1.Withdraw 2.TransactionDetails 3.CheckBalance 4.ChangePin 5.FastCash 6.FundTransaction 7.Exit 8.Login-another-AC");
            int chooseOption=input.nextInt();
            switch (chooseOption){

                case 1: atmManagement.withDraw(accountHolderDetails,cardNumber,transactionDetails);
                    break;
                case 2: atmManagement.transactionDetails(cardNumber);
                    break;
                case 3: atmManagement.checkBalance(accountHolderDetails,cardNumber);
                    break;
                case 4: atmManagement.changePin(accountHolderDetails,cardNumber);
                    break;
                case 5: atmManagement.fastCash(cardNumber,accountHolderDetails,transactionDetails);
                    break;
                case 6:atmManagement.fundTransaction(accountHolderDetails,cardNumber,transactionDetails);
                    break;
                case 7:return ;
                case 8:main(null);
            }
            accountHolderTransaction.put(cardNumber,transactionDetails);
        }

    }
    // All the Transaction option methods


    //withdrew amount using this method
    private void withDraw(Map<String,Map<String,String>> accountHolderDetails,String cardNumber,List<String> transactionDetails)
    {
        System.out.println("withdraw option");
        System.out.println("Enter the amount to withDraw");
        Scanner input=new Scanner(System.in);
        int withDrewAmount=input.nextInt();
        if(withDrewAmount<Integer.parseInt(getCardBalance(accountHolderDetails,cardNumber)))
        {
            boolean withDrewAmountVerify=false;
            int remainingAttempts=3;
            while(!withDrewAmountVerify && remainingAttempts>0)
            {
                withDrewAmountVerify=withDrewAmountPinVerify(accountHolderDetails,cardNumber);
                remainingAttempts--;
            }
            if(!withDrewAmountVerify)
            {
                System.out.printf("Incorrect PIN. Please try again. You have %d attempts remaining.\n", remainingAttempts);
            }
            if(withDrewAmountVerify)
            {
                System.out.println("please wait for you transaction");
                System.out.println("Withdrew Successful of" + withDrewAmount+"/-Rs");
                int remainingBalance=Integer.parseInt(getCardBalance(accountHolderDetails,cardNumber))-withDrewAmount;
                putRemainingBalance(accountHolderDetails,cardNumber,remainingBalance);
                transactionDetails.add("withdraw amount of"+withDrewAmount+"-/Rs");
            }else{
                System.out.println("Sorry lot of trails happen so please contact your Bank");
            }
        }
    }
    // to verify the pin method was to verify the pin to all the transaction like withdraw,fastCash and also fundTransform.
    private boolean withDrewAmountPinVerify(Map<String, Map<String, String>> accountHolderDetails, String cardNumber) {
        Scanner input=new Scanner(System.in);
        //no implemented the option of current or savings account
        System.out.println("Enter the pin Number to conform you Transaction");
        int pinNumber=input.nextInt();
        return Integer.toString(pinNumber).length() == 4 && Integer.toString(pinNumber).equals(getCardPassword(accountHolderDetails, cardNumber));
    }
    // end of withdraw amount


    //it will be developed in future
    private void transactionDetails(String cardNumber)
    {
        System.out.println("Transaction option");
        List<String> transactionDetails = accountHolderTransaction.get(cardNumber);

        if (transactionDetails != null && !transactionDetails.isEmpty()) {
            // Iterate through the list and print each transaction detail
            for (String detail : transactionDetails) {
                System.out.println(detail);
            }
        } else {
            System.out.println("No transaction details available.");
        }
    }
    //To check the Balance
    private void checkBalance(Map<String,Map<String,String>> accountHolderDetails,String cardNumber) {
        System.out.println("CheckBalance option");
        System.out.println("The Account Balance is "+getCardBalance(accountHolderDetails,cardNumber)+" /-Rs");
    }


    //To change the Pin_number :
    String newPinNumber;
    private void changePin(Map<String,Map<String,String>> accountHolderDetails,String cardNumber) {

        boolean verifyChangePin=false;
        int remainingAttempts=3;

        while (!verifyChangePin && remainingAttempts > 0){
            verifyChangePin =verifyChangePin(accountHolderDetails,cardNumber);
            remainingAttempts--;

            if (!verifyChangePin) {
                System.out.printf("Incorrect PIN. Please try again. You have %d attempts remaining.\n", remainingAttempts);
            }
        }
        if (verifyChangePin) {
            System.out.println("OldPin is verified!");

            boolean verifyNewChangePin=false;
            int attemptsRemaining=3;
            while(!verifyNewChangePin && attemptsRemaining>0)
            {
                verifyNewChangePin=verifyNewChangePin();
                attemptsRemaining--;
                if (!verifyNewChangePin) {
                    System.out.printf("Incorrect PIN. Please try again. You have %d attempts remaining.\n", attemptsRemaining);
                }
            }
            if(verifyNewChangePin)
            {
                System.out.println(replaceOldWithNewPassword(accountHolderDetails,cardNumber, Integer.parseInt(newPinNumber)));
            }else{
                System.out.println("Sorry lot of trails happen so please contact your Bank");
            }

        } else {
            System.out.println("PIN limit exceeded. Your card is locked. Please contact your bank.");
        }

    }

    //verify the old pin
    private boolean verifyChangePin(Map<String,Map<String,String>> accountHolderDetails,String cardNumber) {
        Scanner input =new Scanner(System.in);
        System.out.println("Enter the Old Password");
        int oldPassword=input.nextInt();
        String oldPin=Integer.toString(oldPassword);
        return oldPin.length() == 4 && oldPin.equals(getCardPassword(accountHolderDetails, cardNumber));
    }
    private boolean verifyNewChangePin()
    {
        Scanner input =new Scanner(System.in);
        System.out.println("Enter the New PinNumber");
        int newPin=input.nextInt();
        String newPassword=Integer.toString(newPin);
        if(newPassword.length()==4)
        {
            newPinNumber=newPassword;
            return true;
        }else{
            return false;
        }
    }
    //end of changing the pin_Number

    //it will be developed in future
    private void fastCash(String cardNumber,Map<String,Map<String,String>> accountHolderDetails,List<String> transactionDetails) {
        Scanner input=new Scanner(System.in);
        System.out.println("FastCash option");
        System.out.println("Choose the fastCash Amount");
        System.out.println("1.100 2.200 3.500 4.1000");
        int fastCashAmount=0;
        int choice=input.nextInt();
        switch (choice) {
            case 1 -> fastCashAmount = 100;
            case 2 -> fastCashAmount = 200;
            case 3 -> fastCashAmount = 500;
            case 4 -> fastCashAmount = 1000;
        }
        System.out.println("your chosen amount is " + fastCashAmount + "/-rs");
        boolean pinVerification=false;
        int remainingAttempts=3;
        while(!pinVerification && remainingAttempts>0)
        {
            pinVerification=withDrewAmountPinVerify(accountHolderDetails,cardNumber);
            remainingAttempts--;
        }
        if(!pinVerification)
        {
            System.out.printf("Incorrect PIN. Please try again. You have %d attempts remaining.\n", remainingAttempts);
        }
        if(pinVerification)
        {
            System.out.println("please wait for you transaction");
            System.out.println("Withdrew Successful of" +fastCashAmount +"/-Rs");
            // after the withdrawal successful calculate with remaining amount and add back to the details
            int remainingBalance=Integer.parseInt(getCardBalance(accountHolderDetails,cardNumber))-fastCashAmount;
            putRemainingBalance(accountHolderDetails,cardNumber,remainingBalance);
            transactionDetails.add("your fast cash Withdrawal amount of  "+fastCashAmount+"/-Rs");
        }else{
            System.out.println("Sorry lot of trails happen so please contact your Bank");
        }

    }
    //it will be developed in future
    private void fundTransaction(Map<String,Map<String,String>> accountHolderDetails,String cardNumber,List<String> transactionDetails) {
        Scanner input = new Scanner(System.in);
        System.out.println("FundTransaction option");
        System.out.println("Enter the Account Number to Transform");
        String reciverCardNumber=input.next();
        System.out.println("Enter the Amount to transform");
        int amountToBeTransform=input.nextInt();
        if(amountToBeTransform<Integer.parseInt(getCardBalance(accountHolderDetails,cardNumber)))
        {
            boolean pinVerification=false;
            int remainingAttempts=3;
            while(!pinVerification && remainingAttempts>0)
            {
                pinVerification=withDrewAmountPinVerify(accountHolderDetails,cardNumber);
                remainingAttempts--;
            }
            if(!pinVerification)
            {
                System.out.printf("Incorrect PIN. Please try again. You have %d attempts remaining.\n", remainingAttempts);
            }
            if(pinVerification)
            {
                System.out.println("please wait for you transaction");
                System.out.println("Withdrew Successful of" + amountToBeTransform+"/-Rs");
                // after the withdrawal successful calculate with remaining amount and add back to the details
                int remainingBalance=Integer.parseInt(getCardBalance(accountHolderDetails,cardNumber))-amountToBeTransform;
                putRemainingBalance(accountHolderDetails,cardNumber,remainingBalance);
                transactionDetails.add("your fast cash Withdrawal amount of  "+amountToBeTransform+"/-Rs");
            }else{
                System.out.println("Sorry lot of trails happen so please contact your Bank");
            }
        }else{
            System.out.println("Insufficient Balance");
        }

    }



}





