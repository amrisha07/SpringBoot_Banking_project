### Objective

Your assignment is to build an internal API for a fake financial institution using Java and Spring.

### Project Details

- Project has a main file with name FinanceApplication.java which can be used to run the project as a java application. Right click on this file and go to 'Run As' option which has java application option availible.

- There are two controller class
	- Bank Account controller
	- Customer controller
	
- Bank Account controller class has below api's
	- POST http://localhost:8080/bankDetails/addBank/{customerId}
	- GET http://localhost:8080/bankDetails/{customerId}
	- PUT http://localhost:8080/bankDetails/{customerId}
	- DELETE http://localhost:8080/bankDetails/{customerId}/{bankId}
	- GET AMOUNT (GET) http://localhost:8080/bankDetails/{customerId}/{bankId}
	- TRANSFER AMOUNT (PUT) http://localhost:8080/transferAmount
	
- Customer controller class has below api's
	- POST http://localhost:8080/customer
	- GET http://localhost:8080/allCustomer
	- PUT http://localhost:8080/customer/{customerId}
	- DELETE http://localhost:8080/customer/{customerId}

- After running the application it is required to add the customers first in order to proceed any further. Adding bank accounts for a customer should be the second step.

- There are two interfaces in the service package with names mentioned below
	- BankAccountService
	- CustomerService
	
- There are two more classes in the service package which serve as the implementation classes for the above mentioned interfaces. They are named as
	- BankAccountServiceImpl
	- CustomerServiceImpl
	
- There re two interfaces in the repository package. They support while performing CRUD options. There names are mentioned below
	- BankAccountDao
	- CustomerDao
	
- The project contains two entity classes and they are
	- BankAccount
	- Customer
	
- Both the entity classes help in object relational mapping with the schema. There is one to many mapping between customer and bank account.

- There is one DTO class with name 'TransferAmountDto' is created. This DTO class is being used during Transfer amount api call. 

### Dependencies used

- Project uses following Dependencies
	- Spring boot data jpa
	- Spring web
	- H2 database
	- Spring boot test
	- Devtools
	- Commons logging
	
### Api call

- Save Customer
	- link: http://localhost:8080/customer
	- Http method: POST
	- Body: consumes list of customers
	
		[
			{
				"customerId": 1,
				"customerName": "Arisha Barron"
			},
			{
				"customerId": 2,
				"customerName": "Branden Gibson"
			},
			{
				"customerId": 3,
				"customerName": "Rhonda Church"
			}
		]
		
- Get Customer
	- link : http://localhost:8080/allCustomer
	- Http method: GET
	- Body: NA
	- Response: List of all the customers
	
- Update Customer
	- link: http://localhost:8080/customer/1
	- Http method: PUT
	- Body: Takes a customer with updated details
	
		{
			"customerId": 1,
			"customerName": "Sabrina Rust"
		}

- Delete Customer
	- link: http://localhost:8080/customer/1
	- Http method: DELETE
	- Body: NA
	
- Save Bank Account
	- link: http://localhost:8080/bankDetails/addBank/2
	- Http method: POST
	- Body: List of bank accounts
	
		[
			{
				"accountNumber": "01",
				"amount": "2000",
				"bankId": "1235",
				"bankName": "icici",
				"customer": {
					"customerId": 2,
					"customerName": "Branden Gibson"
				}
			},
			{
				"accountNumber": "02",
				"amount": "2400",
				"bankId": "1234",
				"bankName": "hdfc",
				"customer": {
					"customerId": 2,
					"customerName": "Branden Gibson"
				}
			},
			{
				"accountNumber": "03",
				"amount": "2500",
				"bankId": "1236",
				"bankName": "boi",
				"customer": {
					"customerId": 2,
					"customerName": "Branden Gibson"
				}
			}
		]
		
- Get Bank Details
	- link: http://localhost:8080/bankDetails/1
	- Http method: GET
	- Body: NA
	- Response: List of all the banck accounts associated with a Customer
	
- Update Bank Account
	- link: http://localhost:8080/bankDetails/1
	- Http method: PUT
	- Body: Bank account with its Details
			{
				"accountNumber": "01",
				"amount": "2100",
				"bankId": "1235",
				"bankName": "icici",
				"customer": {
					"customerId": 1,
					"customerName": "Arisha Barron"
				}
			}
			
- Delete Bank Account
	- link: http://localhost:8080/bankDetails/1/1235
	- Http method: DELETE
	- Body: NA
	
- Get amount present in a bank account of a perticular Customer
	- link: http://localhost:8080/bankDetails/1/1234
	- Http method: GET
	- Body: NA
	- Response: Amount present in the bank Account
	
- Tranfer Amount from one account to another (it is mandatory that customer and bank account should be created before performing this operation)
	- link: http://localhost:8080/transferAmount
	- Http method: PUT
	- Body: Transfer Amount Dto with details related to source and destination bank account
	
