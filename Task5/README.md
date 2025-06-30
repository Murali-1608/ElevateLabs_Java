# 🏦 Bank Management System (Java)

An OOP-based simulation of a real-world banking system. Users can add, delete, and access multiple customer accounts using a command-line interface.

---

## ✅ Features

- ➕ Add new customers (account creation)
- 🗑️ Delete existing customers
- 📜 View all customers with details
- 💰 Deposit & Withdraw funds
- 📈 Apply interest (SavingsAccount only)
- 📄 Transaction history per customer
- 👨‍👩‍👧‍👦 Multi-customer support using `ArrayList`

---

## 🧱 Class Design

| Class            | Role                              |
|------------------|-----------------------------------|
| `Account`        | Base account class                |
| `SavingsAccount` | Inherits Account, adds interest   |
| `BankSystem`     | Main driver class with menu logic |

---
