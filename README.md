# DepositPlanCase
Deposit Plan Case
Background information
At StashAway our customers can decide to open up one or multiple portfolios to save for
different purposes (for example: a retirement portfolio and an emergency fund portfolio).
Customers deposit funds into StashAway via bank transfer and have to include their
personal StashAway reference code when submitting the transfer to their bank. This
reference code is unique per customer and therefore does not include any information into
which portfolio the funds shall be directed. As such, customers are encouraged to set up
what we call a “deposit plan” for their account on either a one-time and/or monthly basis to
specify how they want funds that are incoming to be allocated amongst their portfolios. The
monthly plans exist for convenience purposes, so that customers can set-up a recurring
deposit and don’t have to login regularly to specify the split.
Please feel free to explore either our FAQs or our product in case you have questions about
particular aspects of deposits at StashAway. You don’t have to replicate StashAway’s logic,
as long as the solution you build is reasonable for the customer.

Example: A customer has two portfolios:
- High risk
- Retirement

As well as two deposit plans:
- One time (High risk: $10,000, Retirement: $500)
- Monthly (High risk: $0, Retirement: $100)

The customer made two fund deposits:
- First deposit: $10,500
- Second deposit: $100

The result of the fund deposit allocation should be:
- High risk portfolio gets 10,000$
- Retirement portfolio gets 600$

Keep in mind that this is a "happy" case.

Task
Your input consists of a list of one one-time and/or one monthly deposit plan (maximum two
deposit plans total), as well as a list of fund deposits for a particular customer. When you
design the data structures, each deposit plan must at least contain the portfolio as well as
the absolute amount of money to allocate to that portfolio, and fund deposits must at least
contain an absolute money amount deposited. Please build a method that can be called
with the described input of both one-time and monthly deposit plans as well as a list of fund
deposits, that returns the allocation of funds amongst the customer’s portfolios. Keep in
mind that all deposits must be distributed fully.
Please submit your solution using https://
