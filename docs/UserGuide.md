---
layout: page
title: User Guide
---

UniCa$h **is a desktop application used for university students who want to be more financially conscious,
optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface
(GUI). If you can type fast, UniCa$h can get your finance management tasks done faster than traditional GUI apps.

<div class="callout callout-important" markdown="span">
Please read through sections [Installation](#installation) and [Command Breakdown](#command-breakdown) before approaching any command documentation
</div>

{% include toc.html %}

---

## Quick Start

<div class="callout callout-important" markdown="span">
Please read through sections [Installation](#installation) and [Command Breakdown](#command-breakdown) before approaching any command documentation
</div>

### Installation

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `unicash.jar` from [GitHub](https://github.com/AY2324S1-CS2103-T16-3/tp/releases/tag/v1.3.1).

3. Copy the file to the folder you want to use as the _home folder_ for your UniCa$h.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar unicash.jar` command to
   run the application.

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press `Enter` to execute it. e.g. typing **`help`** and pressing `Enter` will
   open the help window.

   To get started with UniCa$h, you can run the `add_transactions` command!

6. Refer to the [Features](#features) below for details of each command.

### Command Breakdown

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
This section is important in understanding the constraints and components of a command. All constraints apply to all usages of these prefixes in the commands.
</div>

Commands in UniCa$h have the following structure:

<p style="text-align: center;">
`command_word (ARGUMENT) (PREFIXES)`
</p>

| command_word                                                                                                    | ARGUMENT                                                                                                      | PREFIXES                                                                                                               |
|-----------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| Represents the command to run. May be referenced by alternative shorthands as described in each command section | Comes before all prefixes and often used to reference an index within the transactions list<br>Often optional | Often referred to as "Parameters"<br>Commonly used to specify various attributes/properties for a given `command_word` |

#### Argument Types

| Argument              | Meaning                                  | Constraints                                                                                               | Remarks                                                                          |
|-----------------------|------------------------------------------|-----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `INDEX`<sup>1,2</sup> | Index of transaction in transaction list | Integer indices that must be `>= 1` (positive integer) and are one-indexed, i.e. start with `1`, not `0`. | Commonly used in edit and delete to reference transactions in a transaction list |

**Notes:**

1. `INDEX` uses positive integers which we define as integers that are strictly greater than `0`. 
2. UniCa$h divides the error handling for `INDEX` into two cases, non-positive integers, i.e. `<= 0` values, are treated as invalid command formats while values that exceed the transaction list will be treated as being an invalid index as the supported values are `[1, transaction list size]`.

<div class="callout callout-info" markdown="span">
For more clarity about how commands are parsed and why `INDEX` is parsed this way, please refer to our [developer guide](DeveloperGuide.html#delete-transaction) on how some commands like `delete_transaction` handles `INDEX`.
</div>

#### Prefixes

Prefixes are in the format: 

<p style="text-align: center;">
`prefix/Value`
</p>

Prefixes have several variations with different notations:

||Mandatory|Optional<sup>1</sup>|
||---------|--------|
|Not variadic|`prefix/Value`|`[prefix/Value]`|
|Variadic|`prefix/Value...`|`[prefix/Value]...`|

**Notes:**

1. Optional fields imply that the _omission_, not the absence of value when used, is supported. This means that `l/` is **_NOT_** an optional parameter, but rather a blank one.

#### Prefix Types

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The constraints for the prefixes used in UniCa$h are universal across all commands.
<br><br>
Note that each command might use the prefixes slightly differently so refer to each command's details for more information.
</div>

| Prefix                                                                 | Constraints                                                                                                                                           | Remarks                                                                                                                                                                                                  | Valid                                                                             | Invalid                                               |
|------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|-------------------------------------------------------|
| `n/`<br><br>(Transaction name)                                         | At least 1 character but no more than 500 characters.<br><br>Only supports alphanumeric characters, spaces, (, ), _, @, -, #, &, ., and , characters. | Blank names are not allowed.                                                                                                                                                                             | `n/Hi (John)`                                                                     | `n/`<br>`n/Two ^`                                     |
| `type/`<br><br>(Type of transaction)                                   | Only supported values are `expense` and `income`.                                                                                                     | Case-sensitive, any other values, including blank values (i.e. `type/`), will be rejected.                                                                                                               | `type/expense`<br>`type/income`                                                   | `type/`<br>`type/EXPENSE`<br>`type/hi`                |
| `amt/`<sup>1</sup><br><br>(Monetary amount of budget and transactions) | Values must be `>= 0.00` and `<= 2,147,483,647`.                                                                                                      | Supported inputs allow an optional leading `$` character and all amount values are rounded to the nearest 2 decimal places so `$0.001` will be treated as `$0.00`.                                       | `amt/0`<br>`amt/$10.09`<br>`amt/$0.00`                                            | `amt/`<br>`amt/-100`<br>`amt/hi`<br>`amt/%0.00`       |
| `dt/`<br><br>(Date & time of transaction)                              | Only supported formats are: `dd-MM-yyyy HH:mm`, `yyyy-MM-dd HH:mm`, and `dd MMM yyyy HH:mm`                                                           | If no value is provided, i.e. `dt/`, then it defaults to the current date time when the command is run, using the same date time as the user's system clock.                                             | `dt/`<br>`dt/15-02-2023 14:30`<br>`dt/2023-02-15 14:30`<br>`dt/15 Feb 2023 14:30` | `dt/15 August 2023`<br>`dt/ 14:30`<br>`dt/15-11-2023` |
| `l/`<br><br>(Location of transaction)                                  | At least 1 character but no more than 500 characters.<br><br>Only supports alphanumeric characters, spaces, (, ), _, @, -, #, &, ., and , characters. | Blank locations are not allowed.<br><br>Omit this prefix entirely to indicate that there is no location, `l/` alone is not permitted (blank location).                                                   | `l/NTUC @ UTown`                                                                  | `l/`<br>`l/Two ^`                                     |
| `c/`<br><br>(Category of transaction)                                  | At least 1 character but no more than 15 characters.<br><br>Only supports alphanumeric characters.                                                    | Blank categories are not allowed.<br><br>Omit this prefix entirely to indicate that there is no category, `c/` alone is not permitted (blank category).<br><br>Categories are always saved in lowercase. | `c/Hi`<br>`c/JustExactly15Ch`                                                     | `c/`<br>`c/Over15Characters`<br>`c/#books`            |
| `month/`<br><br>(Month that transaction was performed)                 | Values must be `>= 1` and `<= 12`                                                                                                                     | Assumes January corresponds to `1`, February to `2` and so on.                                                                                                                                           | `month/1`<br>`month/10`<br>`month/12`                                             | `month/`<br>`month/0`<br>`month/-10`<br>`month/15`    | 
| `year/`<br><br>(Year that transaction was performed)                   | Values must be `>= 1920` and `<= 2,157,483,647`                                                                                                       | Intentional restriction to have years be `>= 1920`.                                                                                                                                                      | `year/1920`<br>`year/2023`                                                        | `year/`<br>`year/1919`                                |
| `interval/`<sup>2</sup><br><br>(Budget interval)                       | Only supported values are `day`, `week`, and `month`.                                                                                                 | Case-sensitive, any other values, including blank values (i.e. `interval/`), will be rejected.                                                                                                           | `interval/day`<br>`interval/week`<br>`interval/month`                             | `interval/`<br>`interval/DAY`<br>`interval/hi`        |

**Notes:**

1. Amounts can be exactly `$0.00` as users may want to simply track that a transaction is present but not specify the amount.
2. Intervals work by filtering by the specified time period. 
   1. For `day` intervals, only transactions of the same day are found. 
   2. For `week` intervals, only transactions of the same [week of year](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/temporal/WeekFields.html#weekOfYear()) are found. 
   3. For `month` intervals, only transactions of the same month are found.

### UI Layout

UniCa$h is designed with users who prefer to use the keyboard in mind. Thus, almost all
user input is designed for CLI-type usage, i.e. text-based keyboard input, and UI elements are intended 
to supplement this main functionality.

When UniCa$h is first opened, it would look something like this:

![img_1.png](images/unicash/UniCashWelcome.png)

By default, the Welcome Message will be displayed in the `Results Display`.
This message can also be invoked with the `help` command which will be explained
later on in this User Guide. Below are the main User Interface (UI) component features
we have implemented in UniCa$h.

![img_2.png](images/unicash/UniCashUIAnnotated.png)

Explained below are the main UI components. For the purposes of demonstrating certain UI features, certain commands
and inputs that are yet to be explained are mentioned here. However, at a later section of this User Guide,
all of these commands and inputs will be explained, feel free to refer to them at your discretion. _Where applicable,
consider those explanations as the single source of authority for those commands as the representation here is merely
for UI demonstration purposes only._


#### UniCa$h Main Window Components

- The Main Window in UniCa$h is resizeable, but has a minimum size enforced.
- The Menu bar contains the `File` and `Help` menus, of which `Help` can be opened with the
`F1` keyboard shortcut, which is also default to the original `AB-3`.


- _Note: On macOS, using UniCa$h in fullscreen will cause the Summary Window and Help Window to also
open in fullscreen, however this is an expected behaviour caused by macOS's window management style, and does not
cause any functional issues._ 

#### Command Box
- The `Command Box` is the primary means by which the user interacts actively with the application.
- The user types specific inputs into the `Command Box` and presses `ENTER` after typing to "communicate" with UniCa$h.
- The responses from UniCa$h for each input will be as defined in the subsection for each command in the later part of this User Guide
- Given that our application is targeted for users who prefer CLI-type text input interaction, the `Command Box`
is configured such that it can remember up to `10` latest user inputs. 
  - When a user presses `ENTER` on any input, the input is stored regardless of its validity.
  - These inputs can be traversed through with the `UP` and `DOWN` arrow keys on the keyboard.
  - Only the `10` most recent inputs are stored by the `Command Box`

  - _Note that due to a JavaFX built-in cursor control functionality (i.e. arrows keys can be used to navigate the menu bar),
  the mouse cursor being too close to the menu can occasionally trigger this functionality instead, simply move your
  mouse cursor away from the menu bar if this happens to alleviate the issue._
- At any point in time, the user can press the `ESC` to empty the current text field in the `Command Box` 

#### Transactions List
- Each transaction input by the user is displayed in the `Transactions List`. 
- The entire `Transactions List` will be displayed initially, however certain commands might limit this
listing, which can be reversed with the `list` command to show the entire list again.
- The `Transactions List` is ordered by the time at which the user inputs the transaction,
not the actual date and time associated with that particular transactions.
- Transactions added will immediately appear at the top of the `Transactions List`, and this is to
provide immediate response to the user as they will be able to see their most recently input 
transaction right away.
- The most recent transactions appear at the top of the `Transactions List`.

#### Transaction
- The `Transactions List` contains individual `Transactions` that look like this:

![img_1.png](images/unicash/TransactionCardAnnotated.png)

- **Transaction ID/Index/Number:** All terms used synonymously to refer to the number shown on the left partition of the blue box. Based
on the configuration of `Transactions List`, this number might change, and that is the intended effect, the use for which 
will be explained in the applicable commands, including `delete`, `edit` and `get` commands.
- **Transaction Name:** The name of the given transaction, shown on the right partition of the blue box.
- **Transaction Date:** The date assigned to the transaction, shown inside the pink box.
- **Transaction Location:** The location assigned to the transaction, shown inside the red box.
- **Transaction Categories:** The category/categories assigned to the transaction, shown inside the yellow box.
- **Transaction Amount:** The expense or income assigned to the transaction, shown inside the black box.
  - Expenses will be preceded with a `negative` sign and in red color, whereas incomes will not be preceded with any
  sign and displayed in green color. 
  - _This applies to the amount `0.00` as well, thus display color
is based only on transaction type`._


Note: Certain properties above (such as name, location, categories and amount)
are allowed values that exceed the UI's capacity to display them fully. For 
example, a transaction name that is too long will be shortened by the application,

![img_1.png](images/unicash/TransactionCardFull.png)

This effect is accounted for as we do not wish to limit the user to arbitrary
lengths. Thus, the `get` command is available to retrieve the full, expanded details
of these transactions and display them in the `Results Display` component.

#### Results Display

- The `Results Display` is the primary means by which UniCa$h "responds" back
to the user via text output.
- The `Results Display` can be scrolled if the text output displayed is too long.

#### Data Source Indicator

- The `Data Source Indicator` shows the location of the current UniCa$h storage file.


#### Rolling Balance Indicator

- The `Rolling Balance Indicator` shows the net sum (i.e. `total income - total expense`) 
for the **currently displayed `Transactions List`**.
- For example, if the input `find n/friends` was used to find transactions whose names 
contain the keyword `friends`, the `Transactions List` would be updated to
only show matching transactions and likewise the `Rolling Balance Indicator` 
would reflect the net sum for these transactions.
- If the currently displayed `Transactions List` is the entire list (i.e. after using 
the `list` command), then the `Rolling Balance Indicator` would show the net sum of all 
transactions in UniCa$h. 
- _Note: Unlike the color of an amount of a transaction in `Transactions List`, the color of
the `Rolling Balance Indicator` will change based on whether the net sum is positive (green)
or negative (red) or zero (black)._

[//]: # ()

[//]: # (UI layout and description of what each section means)


---

## Features

### Features Overview

UniCa$h comprises of four primary feature groups:

- [Transaction Management](#transaction-management)
    - [Add Transaction](#add-transaction) (`add_transaction`)
    - [Delete Transaction](#delete-transaction) (`delete_transaction`)
    - [Edit Transaction](#edit-transaction) (`edit_transaction`)
    - [List Transactions](#list-transactions) (`list`)
    - [Get Transaction](#get-transaction) (`get`)
    - [Find Transaction(s)](#find-transactions) (`find`)
    - [Clear All Transactions](#clear-transactions) (`clear_transactions`)
- [Budget Management](#budget-management)
    - [Set Budget](#set-budget) (`set_budget`)
    - [Clear Budget](#clear-budget) (`clear_budget`)
    - [Get Budget](#get-budget) (`get_budget`)
- [Financial Statistics](#financial-statistics)
    - [Get Total Expenditure](#get-total-expenditure) (`get_total_expenditure`)
    - [Summary Statistics](#summary-statistics) (`summary`)
- [General Utility](#general-utility)
    - [Show Help](#help) (`help`)
    - [Reset UniCa$h](#reset-unicah) (`reset_unicash`)
    - [Exit UniCa$h](#exit-unicah) (`exit`)

The instructions for the usage of each command within each feature group are elaborated in the sections below.

### Transaction Management

Transaction management allows users to directly manage their personal finances by adding, editing, deleting, and
finding transactions, among other management features.

#### Add Transaction

Adds a new `Transaction` to UniCa$h.

Command: `add_transaction n/NAME type/TYPE amt/AMOUNT [dt/DATETIME] [l/LOCATION] [c/CATEGORY]`

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
For more information about the prefix constraints, refer to the [command breakdown's prefix types section](#prefix-types)
</div>

Command Words Accepted: `add_transaction`, `add`, `at` (case-insensitive)

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
**Added Constraints**
<br><br>
There is a limit of 5 `Category` that can be added to a `Transaction`. <br/>
There is a limit of 100,000 transactions you can add to UniCa$h.
</div>

##### Successful Execution

**Example 1**

> **Case**: Add transaction with name, amount, type, datetime, location and a category.
>
> **Input**: `add_transaction n/Buying groceries type/expense amt/300 dt/18-08-2023 19:30 l/ntuc c/household`
>
> **Output**:
> ```
> New transaction added:
> 
> Name: Buying groceries;
> Type: expense;
> Amount: $300.00;
> Date: 18 Aug 2023 19:30;
> Location: ntuc;
> Categories: #household
> ```

**Example 2**

> **Case**: Add transaction with name, amount and type.
>
> **Input**: `add_transaction n/Working type/income amt/8000`
>
> **Output**:
> ```
> New transaction added:
> 
> Name: Working;
> Type: income;
> Amount: $8000.00;
> Date: 28 Oct 2023 19:01;
> Location: -;
> Categories:
> ```

##### Failed Execution

**Example 1**

> **Case**: Missing compulsory fields.
>
> **Input**: `add_transaction`
>
> **Output**:
> ```
> Invalid command format! 
>
> add_transaction: Adds a transaction to UniCa$h.
>
> Parameters: n/NAME type/TYPE amt/AMOUNT dt/DATETIME l/LOCATION [c/CATEGORY]...
>
> Example: add_transaction n/Buying groceries type/expense amt/300 dt/18-08-2023 19:30 l/ntuc c/household
> ```

**Example 2**

> **Case**: Duplicate categories with valid compulsory fields.
>
> **Input**: `add_transaction n/Buying groceries type/expense amt/300 c/household c/household`
>
> **Output**:
> ```
> All categories must be case-insensitively unique, duplicate categories are not allowed.
> ```

**Example 3**

> **Case**: More than 5 categories with valid compulsory fields.
>
> **Input**: `add_transaction n/Buying groceries type/expense amt/300 c/household c/entertainment c/education c/fun c/school c/test`
>
> **Output**:
> ```
> There should only be a maximum of 5 unique categories.
> ```

**Example 4**

> **Case**: More than 100,000 transactions added.
>
> **Input**: `add n/test amt/300 type/expense`
>
> **Output**:
> ```
> UniCa$h supports up to a maximum of 100,000 transactions.
> ```

#### Edit Transaction

Edits an existing transaction in UniCa$h.

Command: `edit_transaction INDEX [n/NAME] [type/TYPE] [amt/AMOUNT] [dt/DATETIME] [l/LOCATION] [c/CATEGORY]`

Command Words Accepted: `edit_transaction`, `edit`, `et` (case-insensitive)

Command Options:

| Option Name | Optional? | Purpose                                                                                             |
|-------------|-----------|-----------------------------------------------------------------------------------------------------|
| INDEX       | No        | (One-based) Index of the transaction to edit.                                                       |
| n/          | Yes       | Name of the transaction.                                                                            |
| type/       | Yes       | Transaction type of transaction.<br/>Valid types are `income` and `expense`.                        |
| amt/        | Yes       | Monetary amount of transaction. Has to be a positive value.                                         |
| dt/         | Yes       | Date and time where transaction was made.<br/>Defaults to current date time if field is left empty. |
| l/          | Yes       | Location where transaction was made.<br/>Defaults to `'-'` if field is left empty.                  |
| c/          | Yes       | Category tagged to that transaction.<br/>Defaults to no categories if field is left empty.          |

Please refer to the constraints for each prefix listed under the [Prefix Types](#prefix-types) section above.

Important notes:

1. The value of `INDEX` provided must be between 1 and the total number of available transactions (inclusive).
2. While all options besides `INDEX` are optional, **you must specify at least one field** you wish to edit (i.e. `Name`, `Type`, `Amount`, `Datetime`, `Location`, or `Category`).
3. If the `Name`, `Type`, or `Amount` options are present in the edit command, the values provided for these options cannot be empty. (See [Failed Execution (Example 3) below](#example-3-2))
4. **Specially for the Edit feature:** Empty values are allowed for `Datetime`, `Location`, and `Category`. If these options are provided in the edit command but left empty, the default values that these fields will take are stated in the table above. (See [Successful Execution (Example 3) below](#example-3-1))
5. The `INDEX` option must be specified first. The order in which you specify the other options does not matter.
6. Editing a transaction's category will result in the **replacement of all existing categories**. 
   - For example, in the case of a `Transaction` with two existing categories (Entertainment and Hobbies), editing it with `edit INDEX c/Education` will replace all existing categories, leaving the transaction with a single category, "Education".


##### Successful Execution

###### Example 1

> **Case**: Edit an existing transaction's name, amount, type, datetime, location and categories.
>
> **Input**: `edit_transaction 5 n/Tuition Income type/income amt/1000 dt/2023-10-31 10:00 l/John home c/tuition`
>
> **Output**:
> ```
> Edited Transaction:
> 
> Name: Tuition Income;
> Type: income;
> Amount: $1000.00;
> Date: 31 Oct 2023 10:00;
> Location: John home;
> Categories: #tuition
> ```
>
> <img src="images/unicash/command-outputs/editTransaction/editTransactionSuccessOutput1.png" width="1000" />

###### Example 2

> **Case**: Edit an existing transaction's datetime and amount only.
>
> **Input**: `edit_transaction 5 dt/31 Oct 2023 12:00 amt/1200`
>
> **Output**:
> ```
> Edited Transaction:
> 
> Name: Tuition Income;
> Type: income;
> Amount: $1200.00;
> Date: 31 Oct 2023 12:00;
> Location: John home;
> Categories: #tuition
> ```

###### Example 3

> **Case**: Edit an existing transaction's location and categories to default.
>
> **Input**: `edit_transaction 5 l/ c/`
>
> **Output**:
> ```
> Edited Transaction:
>
> Name: Tuition Income;
> Type: income;
> Amount: $1200.00;
> Date: 31 Oct 2023 12:00;
> Location: -;
> Categories:
> ```

##### Failed Execution

###### Example 1

> **Case**: No attributes to edit
>
> **Input**: `edit_transaction 5`
>
> **Output**:
> ```
> At least one field to edit must be provided.
> ```

###### Example 2

> **Case**: No index provided
>
> **Input**: `edit_transaction n/Donation`
>
> **Output**:
> ```
> Invalid command format! 
> 
> edit_transaction: Edits the details of the transaction identified by the index number used in the displayed transaction list.
> 
> Argument: Index (must be a positive integer)
> 
> Parameters: [n/Name] [type/Type] [amt/Amount] [dt/DateTime] [l/Location] [c/Category]...
> 
> Example: edit_transaction 1 n/Buying groceries type/expense amt/300 dt/18-08-2023 19:30 l/NTUC c/Food
> ```

###### Example 3

> **Case**: Attempting to leave compulsory field (E.g. `name`) blank
>
> **Input**: `edit_transaction 5 n/`
>
> **Output**:
> ```
> Names should only contain alphanumeric characters, spaces, (, ), _, @, -, #, &, ., and ',', up to 500 characters and it should not be blank
> ```

###### Example 4

> **Case**: There are only 5 transactions, but the user tries to edit expense 6
>
> **Input**: `edit_transaction 6 n/Dog food`
>
> **Output**:
> ```
> The transaction index provided is invalid
> ```

###### Example 5

> **Case**: Wrong input format (e.g. `Datetime` is not specified in any of the accepted formats)
>
> **Input**: `edit_transaction 5 dt/23 March 2023 8:15pm`
>
> **Output**:
> ```
> DateTime should be in either of the following formats: 
> 1. dd-MM-uuuu HH:mm
> 2. uuuu-MM-dd HH:mm
> 3. dd MMM uuuu HH:mm
> ```

#### Delete Transaction

Deletes a `Transaction` from UniCa$h.

Command: `delete_transaction INDEX`

Command Words Accepted: `delete_transaction`, `delete`, `del` (case-insensitive)

Command Argument: `<INDEX>` is the displayed transaction index
of the transaction to be deleted, as shown in the `Transactions List`.

| Arguments | Optional? | Purpose                                            |
|-----------|-----------|----------------------------------------------------|
| `<INDEX>` | No        | Transaction index of the transaction to be deleted |

Important notes:

1. The `delete_transaction` command word is case-insensitive, thus `DELETE_TRANSACTION` is
   considered an equivalent command word.

2. `<INDEX>` must be a positive integer, i.e. a number greater than 0.

3. `<INDEX>` must be equal to or smaller than 2,147,483,647 which is the `Integer.MAX_VALUE` provided by Java 11.

4. `<INDEX>` must be equal to or smaller than the largest displayed transaction index
   of all transactions as shown in the `Transactions List`. Thus, even if there are `100` total transactions but only
   `20` of those transactions are displayed in the current `Transactions List`, the maximum `INDEX` allowed
   would be `20`

5. Given `3.` and `4.`, the maximum allowed `<INDEX>` is the smaller value of the two.

##### Successful Execution

###### Example 1

> **Case**: Delete a transaction with the correctly specified `<INDEX>`.
>
> **Input**: `delete_transaction 1`
>
> **Output**:
> ```
> Deleted Transaction:
>
> Name: Evening with friends;
> Type: expense;
> Amount: $49.50;
> Date: 17 Sep 2023 00:00;
> Location: Clarke Quay;
> Categories: #social
> ```
> Input:
> <img src="images/unicash/command-outputs/deleteTransaction/deleteTransactionInitialState.png" width="1000" />
> Output:
> <img src="images/unicash/command-outputs/deleteTransaction/deleteTransactionSuccess1FinalState.png" width="1000" />

###### Example 2

> **Case**: Delete a transaction with the currently not visible but correctly specified `<INDEX>`.
>
> **Input**: `delete_transaction 1`
>
> **Output**:
> ```
> Deleted Transaction:
> 
> Name: Lunch at McDonalds;
> Type: expense;
> Amount: $17.40;
> Date: 15 Sep 2023 11:00;
> Location: Clementi Mall;
> Categories: #food
> ```
> Input:
> <img src="images/unicash/command-outputs/deleteTransaction/deleteTransactionInitialState2.png" width="1000" />
> Ouput:
> <img src="images/unicash/command-outputs/deleteTransaction/deleteTransactionSuccess2FinalState.png" width="1000" />

##### Failed Execution

###### Example 1

> **Case**: Missing compulsory fields.
>
> **Input**: `delete_transaction`
>
> **Output**:
> ```
> Invalid command format! 
>
> delete_transaction: Deletes the transaction identified by the index number used in the displayed transaction list.
>
> Argument: Index (must be a positive integer)
>
> Example: delete_transaction 1
> ```
> <img src="images/unicash/command-outputs/deleteTransaction/deleteFail1.png" width="1000" />

###### Example 2

> **Case**: Invalid `<INDEX>` provided
> (`<INDEX>` given as `10` given when only `5` transactions are presently displayed)
>
> **Input**: `delete_transaction 10`
>
> **Output**:
> ```
> The transaction index provided is invalid
> ```
> <img src="images/unicash/command-outputs/deleteTransaction/deleteFail2.png" width="1000" />

#### Get Transaction

Retrieves a `Transaction` from UniCa$h.

Command: `get <INDEX>`

Command Words Accepted: `get`, `g` (case-insensitive)

Command Argument: `<INDEX>` is the displayed transaction index
of the transaction to be retrieved, as shown in the `Transactions List`.

| Arguments | Optional? | Purpose                                              |
|-----------|-----------|------------------------------------------------------|
| `<INDEX>` | No        | Transaction index of the transaction to be retrieved |

Important notes:

1. The `get` command word is case-insensitive, thus `GET` is
   considered an equivalent command word.

2. `<INDEX>` must be a positive integer, i.e. a number greater than 0.

3. `<INDEX>` must be equal to or smaller than 2,147,483,647 which is the `Integer.MAX_VALUE` provided by Java 11.

4. `<INDEX>` must be equal to or smaller than the largest displayed transaction index
   of all transactions as shown in the `Transactions List`. Thus, even if there are `100` total transactions but only
   `20` of those transactions are displayed in the current `Transactions List`, the maximum `INDEX` allowed
   would be `20`

5. Given `3.` and `4.`, the maximum allowed `<INDEX>` is the smaller value of the two.

##### Successful Execution

###### Example 1

> **Case**: Retrieve a transaction with the correctly specified `<INDEX>`.
>
> **Input**: `get 5`
>
> **Output**:
> ```
> Transaction 5 retrieved:
> 
> Name: Taxi;
> Type: expense;
> Amount: $20.00;
> Date: 18 Sep 2023 11:30;
> Location: Bugis;
> Categories: #transport
> ```
> Input:
> <img src="images/unicash/command-outputs/getTransaction/getInitial1.png" width="1000" />
> Ouput:
> <img src="images/unicash/command-outputs/getTransaction/getSuccessFinal1.png" width="1000" />

###### Example 2

> **Case**: Retrieve a transaction with the currently not visible but correctly specified `<INDEX>`.
>
> **Input**: `get 1`
>
> **Output**:
> ```
> Transaction 1 retrieved:
>
> Name: Buy clothes;
> Type: expense;
> Amount: $109.00;
> Date: 17 Sep 2023 18:30;
> Location: Uniqlo Bugis;
> Categories: #shopping
> ```
> Input:
> <img src="images/unicash/command-outputs/getTransaction/getInitial2.png" width="1000" />
> Ouput:
> <img src="images/unicash/command-outputs/getTransaction/getSuccessFinal2.png" width="1000" />

##### Failed Execution

###### Example 1

> **Case**: Missing compulsory fields.
>
> **Input**: `get`
>
> **Output**:
> ```
> Invalid command format! 
>
> get: Displays expanded details of a specific transaction.
>
> Argument: Index (must be a positive integer)
>
> Example: get 2
> ```
> <img src="images/unicash/command-outputs/getTransaction/getFail1.png" width="1000" />

###### Example 2

> **Case**: Invalid `<INDEX>` provided
>
> **Context**: `10` given as `<INDEX>` when only `5` transactions are presently displayed.
>
> **Input**: `get 10`
>
> **Output**:
> ```
> The transaction index provided is invalid
> ```
> <img src="images/unicash/command-outputs/getTransaction/getFail2.png" width="1000" />

#### Find Transaction(s)

Finds a `Transaction` in UniCa$h.

Command: `find [n/NAME] [l/LOCATION] [c/CATEGORY]`

Command Words Accepted: `find`, `search`, `f` (case-insensitive)

Command Argument: `<INDEX>` is the displayed transaction index
of the transaction to be retrieved, as shown in the `Transactions List`.

Command Options:

| Option Name      | Optional?       | Purpose                                                      |
|------------------|-----------------|--------------------------------------------------------------|
| n/               | Yes*            | Search keyword for the name of a transaction.                |
| l/               | Yes*            | Search keyword for the location of a transaction.            |
| c/               | Yes*            | Search keyword for a category tagged to a transaction        |
| Any of the above | Min. one option | At least one option must be specified for the `find` command |

Important notes:
1. The `find` command word is case-insensitive, thus `FIND` is
   considered an equivalent command word.

2. *While each option is considered Optional, at least one option must be specified in total

3. All keywords specified must match in order for a transaction to be displayed.
4. Only one instance of each option can be specified, i.e. `/n Friends n/Dinner` is invalid as the name 
option is specified more than once.
5. For each keyword, a full substring match is required, thus `find n/Lunch with friends` with search for transactions
whose name contains the String `Lunch with friends` and not `Lunch`, `with`, and `friends` separately. However, an 
exact match is not required as a transaction with the name `Lunch with friends outside` contains the above substring
and therefore it will be flagged as a match.

##### Successful Execution

###### Example 1

> **Case**: Retrieve a transaction with the correctly specified options.
>
> **Input**: `find n/friends`
>
> **Output**:
> ```
> 
> 1 Transactions listed!
> 
> ```
> Input:
> <img src="images/unicash/command-outputs/find/FindCommandSuccessInitial1.png" width="1000" />
> 
> Output:
> <img src="images/unicash/command-outputs/find/FindSuccess1.png" width="1000" />

##### Failed Execution

###### Example 1

> **Case**: Command entered with no parameters
>
> **Input**: `find`
>
> **Output**:
> ```
> Invalid command format! 
> 
> find, search, f: Finds all transactions whose properties match all of thespecified keywords (case-insensitive) and displays them as a list with index numbers.
> 
> Only one keyword can be specified for each property and at least one keyword must be provided in total.
> 
> Parameters: [n/Name] [l/Location] [c/Category]
> 
> Example: find, search, f n/Buying groceries type/expense amt/300 dt/18-08-2023 19:30 l/NTUC c/Food
> 
> ```
> Output:
> <img src="images/unicash/command-outputs/find/FindFailure2.png" width="1000" />
> 

#### List Transactions
Shows the list of all transactions in UniCa$h.

Command: `list`

Command Words Accepted: `list`, `ls` (case-insensitive)

##### Successful Execution

###### Example 1

> **Case**: Calling the command when there are no existing transactions.
>
> **Input**: `list`
>
> **Output**:
> ```
> Listed all transactions.
> ```
> Output:
> <img src="images/unicash/command-outputs/list/noTransactions.png" width="1000" />
>
> **Note:** There are no transactions to display, so the GUI will be empty. This is expected behaviour.
###### Example 2

> **Case**: Calling the command with existing transactions.
>
> **Input**: `list`
>
> **Output**:
> ```
> 
> Listed all transactions.
> 
> ```
> Output:
> <img src="images/unicash/command-outputs/list/withTransactions.png" width="1000" />

##### Failed Execution

###### Example 1

> **Case**: Command entered with parameters
>
> **Input**: `list 5`
>
> **Output**:
> ```
> Command not recognised. Try using the command list, list_transactions, ls without any parameters instead.
> ```

#### Clear Transactions
Clears all transactions in UniCa$h.

Command: `clear_transactions`

Command Words Accepted: `clear_transactions` (case-insensitive)

### Budget Management

The budget serves as an observable metric used to allow users to understand when their expenses over a given interval. They can use this information to better understand if they should be controlling their spending or adjusting their budget.

<div class="callout callout-info" markdown="span">
For this team project, we have opted to simplify the budgeting feature by limiting the user to a single budget at a time that can be configured for different intervals and amounts.
</div>

#### Set Budget

Sets the user's budget on UniCa$h to be a given amount and within a given interval.

Command: `set_budget amt/Amount interval/Interval`

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
For more information about the prefix constraints, refer to the [command breakdown's prefix types section](#prefix-types)
</div>

Command Words Accepted: `set_budget`, `sb`, `budget`

##### Successful Execution

**Example 1**

> **Case**: Set budget of $600 for every month.
>
> **Input**: `set_budget amt/600 interval/month`
>
> **Output**:
> ```
> New budget added:
>
> Amount: $600.00;
> Interval: month
> ```

##### Failed Execution

**Example 1**

> **Case**: Missing amount.
>
> **Input**: `set_budget interval/month`
>
> **Output**:
> ```
> Invalid command format! 
>
> set_budget: Sets the user's budget on UniCa$h.
>
> Parameters: amt/Amount interval/Interval
>
> Example: set_budget amt/300 interval/day
> ```

**Example 2**

> **Case**: Missing interval.
>
> **Input**: `set_budget amt/600`
>
> **Output**:
> ```
> Invalid command format! 
>
> set_budget: Sets the user's budget on UniCa$h.
>
> Parameters: amt/Amount interval/Interval
>
> Example: set_budget amt/300 interval/day
> ```

**Example 3**

> **Case**: No fields.
>
> **Input**: `set_budget`
>
> **Output**:
> ```
> Invalid command format! 
>
> set_budget: Sets the user's budget on UniCa$h.
>
> Parameters: amt/Amount interval/Interval
>
> Example: set_budget amt/300 interval/day
> ```

**Example 4**

> **Case**: Negative amount.
>
> **Input**: `set_budget amt/-123 interval/day`
>
> **Output**:
> ```
> Amounts must be within range of [0, 2,147,483,647] and either start with $ or nothing at all
> ```

**Example 5**

> **Case**: Invalid interval value.
>
> **Input**: `set_budget amt/600 interval/hi`
>
> **Output**:
> ```
> Interval value must be one of the following: day, week, month
> ```

#### Clear Budget

Clears the user's budget set in UniCa$h. If no budget is set yet, the user is prompted to set one first instead.

Command: `clear_budget`

Command Words Accepted: `clear_budget`, `cb` (case-insensitive)

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
`clear_budget` will not parse any additional argument or parameters.
</div>

##### Successful Execution

**Example 1**

> **Case**: Clear user set budget.
>
> **Input**: `clear_budget`
>
> **Output**:
> ```
> Budget cleared.
> ```

**Example 2**

> **Case**: Clear without set budget.
>
> **Input**: `clear_budget`
>
> **Output**:
> ```
> No budget to clear.
>
> Consider using set_budget amt/Amount interval/Interval first!
> ```

#### Get Budget

Retrieves the set budget and the spending over the given interval. 

If no budget has been set, the user will be prompted to set one first instead.

The user's spending is calculated by: `budget - interval expenses`.

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
For more information about how intervals are handled, please refer to the [prefix types section.](#prefix-types)
</div>

[//]: # (TODO: Believe this should be resolved soon)
<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The usage is calculated from the list of filtered transactions so to view the budget remainder across expense transactions, use the `list` command first.
</div>

Command: `get_budget`

Command Words Accepted: `get_budget`, `gb` (case-insensitive)

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
`get_budget` will not parse any additional argument or parameters.
</div>

##### Successful Execution

**Example 1**

> **Case**: Get user's set budget and spending remainder.
>
> **Input**: `get_budget`
>
> **Output**:
> ```
> Monthly budget of $600.00
>
> Net amount of $585.00
> ```

**Example 2**

> **Case**: Get budget without budget set.
>
> **Input**: `get_budget`
>
> **Output**:
> ```
> No budget set. Use set_budget amt/Amount interval/Interval
> ```

### Financial Statistics

#### Get Total Expenditure

Retrieves the total expenditure by month with optional filters for category and year. Also filters the transaction list by the given month, year, and category.

[//]: # (TODO: Believe this should be resolved soon)
<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The total expenditure is calculated from the list of filtered transactions so to view the total expenditure across expense transactions, use the `list` command first.
</div>

Command: `get_total_expenditure month/Month [c/Category] [year/Year]`

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
For more information about the prefix constraints, refer to the [command breakdown's prefix types section](#prefix-types)
</div>

Command Words Accepted: `get_total_expenditure`, `get_total_exp`, `gte` (case-insensitive)

##### Successful Execution

**Example 1**

> **Case:** Get total expenditure with month only.
>
> **Input:** `get_total_expenditure month/10`
>
> **Output:**
> ```
> Your total expenditure in October 2023 was $1028.00
> ```
> 
> **Note:** The transaction list will be filtered
> 
> <img src="images/unicash/command-outputs/get-total-expenditure/getTotalExpenditureMonthOnlySuccess.png" width="400">

**Example 2**

> **Case:** Get total expenditure with month and year.
>
> **Input:** `get_total_expenditure month/10 year/2023`
>
> **Output:**
> ```
> Your total expenditure in October 2023 was $1028.00
> ```

**Example 3**

> **Case:** Get total expenditure with month and category.
>
> **Input:** `get_total_expenditure month/9 c/social`
>
> **Output:**
> ```
> Your total expenditure in September 2023 for "social" was $49.50
> ```

**Example 4**

> **Case:** Get total expenditure with month, category, and year.
>
> **Input:** `get_total_expenditure month/9 c/shopping year/2023`
>
> **Output:**
> ```
> Your total expenditure in September 2023 for "shopping" was $109.00
> ```

**Example 5**

> **Case:** Get total expenditure but no matches.
>
> **Input:** `get_total_expenditure month/1`
>
> **Output:**
> ```
> Your total expenditure in September 2023 for "shopping" was $109.00
> ```

##### Failed Execution

**Example 1**

> **Case:** No month provided.
>
> **Input:** `get_total_expenditure`
>
> **Output:**
> ```
> Invalid command format! 
>
> get_total_expenditure: Retrieves the total expenditure by month with optional filters for category and year.
>
> Parameters: month/Month [c/Category] [year/Year]
>
> Example: get_total_expenditure month/10 c/Food year/2006
> ```

**Example 2**

> **Case:** Negative month.
>
> **Input:** `get_total_expenditure month/-10`
>
> **Output:**
> ```
> Month must be between 1 and 12 (inclusive).
> ```

**Example 3**

> **Case:** Month greater than 12.
>
> **Input:** `get_total_expenditure month/14`
>
> **Output:**
> ```
> Month must be between 1 and 12 (inclusive).
> ```

**Example 4**

> **Case:** Month is not an integer.
>
> **Input:** `get_total_expenditure month/hi`
>
> **Output:**
> ```
> Invalid month value, must be an integer!
> ```

**Example 5**

> **Case:** Year is less than 1920.
>
> **Input:** `get_total_expenditure month/9 year/1800`
>
> **Output:**
> ```
> Year must be after 1920.
> ```

**Example 6**

> **Case:** Year is not an integer.
>
> **Input:** `get_total_expenditure month/9 year/hi`
>
> **Output:**
> ```
> Invalid year value, must be an integer!
> ```

**Example 7**

> **Case:** Category contains non-alphanumeric characters.
>
> **Input:** `get_total_expenditure month/9 c/@123`
>
> **Output:**
> ```
> Category names should be alphanumeric and up to 15 characters long.
> ```

**Example 8**

> **Case:** Category length is greater than 15.
>
> **Input:** `get_total_expenditure month/9 c/abcdefghijklmnopqrs`
>
> **Output:**
> ```
> Category names should be alphanumeric and up to 15 characters long.
> ```

#### Summary Statistics

Displays a summary of the expenses saved in UniCa$h.

Command: `summary`

Command Words Accepted: `summary` (case-insensitive)

Important notes:

1. Expected Behaviour: The summary of expenses is a pop-up window. If there are no expenses saved in UniCa$h, the pop-up
   window will not appear.
   - Please be aware that if you open the summary window and subsequently delete all expenses from UniCa$h without
   closing the summary window, it is intended to stay open until you manually close it. In such a scenario, the summary
   window will look like the following:
<img src="images/unicash/command-outputs/summary/summarySuccessOutput3.png" width="1000" />
2. When making changes to your transactions in UniCa$h, the plots in the summary window will automatically update to
   reflect your modifications.
3. The **pie chart** showcases the **top 10 expense categories** based on their respective amounts, while the
   **line chart** exclusively showcases expenses **from the past one year**, according to the system's clock.
   - This indicates that it is possible for only one plot to have data, while the other does not. (E.g. If there are
     only expenses recorded from over a year ago, then only the pie chart will display data, while the line chart will
     not.)

##### Successful Execution

###### Example 1

> **Case**: There are expenses saved in UniCa$h
>
> **Input**: `summary`
>
> **Output**:
>
> The output displayed in the main window is:
> ```
> Opened UniCa$h summary window.
> ```
>
> <img src="images/unicash/command-outputs/summary/summarySuccessOutput1.png" width="1000" />
>
> Here is what the summary pop-up window looks like:
>
> <img src="images/unicash/command-outputs/summary/summarySuccessOutput2.png" width="1000" />

###### Example 2

> **Case**: There are no expenses saved in UniCa$h, and the summary window was not originally opened.
>
> **Input**: `summary`
>
> **Output**:
> ```
> You have no expenses to summarize.
> ```
> <img src="images/unicash/command-outputs/summary/summarySuccessOutput4.png" width="1000" />
> Note: The summary pop-up window does not appear.

### General Utility

#### Help

Get help for UniCa$h.

Command: `help COMMAND_WORD`

Command Words Accepted: `help`, `h` (case-insensitive)

Command Argument: `COMMAND_WORD` is the command to get help for. If no
argument is specified, a general help message is shown as well as a pop up
containing a link to our User Guide.

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
To get a list of `COMMAND_WORD`, do `help` with no arguments
</div>

##### Successful Execution

**Example 1**

> **Case**: Get general help.
>
> **Input**: `help`
>
> **Output**:
> ```
> Welcome to UniCa$h!
>
> For more detailed help on a command: help COMMAND_WORD
>
> Available Commands:
> add
> delete
> edit
> list
> find
> get
> get_total_expenditure
> summary
> set_budget
> get_budget
>
> clear_budget
> clear_transactions
> reset_unicash
>
> help
> exit
> ```
>
> The following output is shown as well as the popup.
> <img src="images/unicash/HelpPopup.png" width="1000" />

**Example 2**

> **Case**: Get general for a specific command.
>
> **Input**: `help add_transaction`
>
> **Output**:
> ```
> add_transaction: Adds a transaction to UniCa$h.
>
> Parameters: n/Name type/Type amt/Amount [dt/DateTime] [l/Location] [c/Category]...
>
> Example: add_transaction n/Buying groceries type/expense amt/300 dt/18-08-2023 19:30 l/NTUC c/Food
> ```

##### Failed Execution

**Example 1**

> **Case**: Get help for an unknown command.
>
> **Input**: `help foo`
>
> **Output**:
> ```
> Unknown command
>
> help: Shows UniCa$h general usage instructions and specific command usage by specifying the command word.
>
> Argument: Command word specified must be a valid command word present in the help command
>
> Example: help add_transaction
> ```

#### Reset UniCa$h
Resets UniCa$h to its default state.

Command: `reset_unicash`

Command Words Accepted: `reset_unicash` (case-insensitive)

#### Exit UniCa$h
Exit UniCa$h.

Command: `exit`

Command Words Accepted: `exit`, `quit`, `bye` (case-insensitive)

### Summary

| Action                      | Format, Examples                                                                                      |
|-----------------------------|-------------------------------------------------------------------------------------------------------|
| **Add Transaction**         | `add_transaction n/Name type/Type amt/Amount [dt/Datetime] [l/Location] [c/Category]...`              |
| **Delete Transaction**      | `delete_transaction INDEX`                                                                            |                                                                                                                                                                                                                       |
| **Delete All Transactions** | `clear_transactions`                                                                                  |
| **Edit Transaction**        | `edit_transaction INDEX [n/Name] [type/Type] [amt/Amount] [dt/Datetime] [l/Location] [c/Category]...` |
| **List All Transactions**   | `list`                                                                                                |
| **Find Transaction(s)**     | `find [n/Name] [c/Category] [l/Location]`                                                             |
| **Get Total Expenditure**   | `get_total_expenditure month/Month [c/Category] [year/Year]`                                          |
| **Summary Statistics**      | `summary`                                                                                             |
| **Set Budget**              | `set_budget amt/Amount interval/Interval`                                                             |
| **Clear Budget**            | `clear_budget`                                                                                        |
| **Get Budget**              | `get_budget`                                                                                          |

[//]: # (## Troubleshoot)

---

## Known Issues

1. Hard to see scrollbar

---

## FAQ

**Q**: How do I transfer my data to another Computer?

**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous UniCa$h home folder.

---

## Acknowledgements

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

---

## Terminology

| Term        | Meaning                                                                                            |
|-------------|----------------------------------------------------------------------------------------------------|
| Transaction | Represents both an expense or an income. Expenses cause a net loss while incomes cause a net gain. |
