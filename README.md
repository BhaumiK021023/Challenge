# Challenge
Module : Account Creation, Find Account by Account ID, Transfer Money between accounts

Implemented transfer money between the accounts simulteniously using Multi thread concept

#**Endpoint**
  #Transfer Money  
 
 URL : localhost:18080/v1/accounts/transfer?fromAccountId=1&fromAccountAmt=500&toAccountId=2&toAccountAmt=300
 OutPut : 
 [
  {
        "accountId": "1",
        "balance": 800
    },
    {
        "accountId": "2",
        "balance": 2200
    }
]
Explaination : -- The amount 500 has been deducted  from fromAcount and credited in to toAccount, and the amount 300 has been deducted from toAccount and creddited into fromAccount


 

