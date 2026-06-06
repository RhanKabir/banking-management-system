## Banking Management System
A desktop banking application built in Java with a MySQL backend. Supports user registration, login, deposits, withdrawals, transfers, and transaction history.

# Features

User registration and login with credential validation

Deposit and withdraw funds

Transfer funds between accounts

View past transaction history

# Database Setup
You will need to set up a local MySQL database to run this project. Once MySQL is installed, 
update the credentials in src/dbs_objs/MyJDBC.java to match your local setup.

# Usage

1. Launch the app — you will be taken to the login screen

2. Click "Register here" to create a new account (username must be at least 6 characters)

3. Log in with your credentials

4. From the main screen you can:

  - Deposit — add funds to your account

  - Withdraw — remove funds (cannot exceed current balance)

  - Transfer — send funds to another registered user by username

  - Past Transactions — view your full transaction history

  - Logout — return to the login screen
