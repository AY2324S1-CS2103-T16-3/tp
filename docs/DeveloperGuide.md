---
layout: page
title: Developer Guide
---

**Team:** T16-03

**Name:** UniCa$h

**User Target Profile:** Our application is for university students who want to be more financially conscious about their spending habits, enabling them to make wiser economic decisions, providing students on a limited budget with more purchasing power.

**Value Proposition:** It provides university students with an intuitive and frictionless experience to have transparency on their expenditure to help them better understand their expenditure so they can develop better financial habits as they transition into adulthood.

{% include toc.html %}

## Getting Started

### Setting up

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
If you are interested in testing out UniCa$h, you can refer to our [quick start](UserGuide.html#quick-start) in the user guide.
</div>

If you are interested in developing for UniCa$h, you can find the setup steps below:

Ensure that the project is set up locally:

1. Create a fork of the GitHub repository
2. Clone the fork of the repository
 
    ```bash
    git clone https://github.com/<your Github username>/tp.git
    ```
3. Change to the fork local directory
 
    ```bash
    cd tp/
    ```
4. Build the project

    ```bash
    ./gradlew build
    ```
5. Run the project

    ```bash
    ./gradlew run
    ```

Then, to contribute to the project, we recommend the following flow:

1. Setup the original repository as the `upstream` remote

    ```bash
    git remote add upstream https://github.com/AY2324S1-CS2103-T16-3/tp.git
    ```
2. Create a feature/fix/improvement local branch

    ```bash
    git fetch upstream master
    git merge upstream/master
    git checkout -b <feat/fix/docs/etc.>/<branch name>
    ```
3. Make the necessary changes
4. Create a Pull Request to the original repository

### Acknowledgements

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

### Terminology

| Term        | Meaning                                                                                                             |
|-------------|---------------------------------------------------------------------------------------------------------------------|
| Transaction | Represents both an expense or an income. Expenses cause a net loss while incomes cause a net gain                   |
| Expenditure | Total amount for transactions labelled as "expense"                                                                 |
| Budget      | Observable metric on expenditure, tracking daily/weekly/monthly (only one) expense relative to preset budget amount |

---

## Feature Tracking

Every user can...

- Track and manage their expenses
- Manage a budget
- View their total expenditure by the month/year
- Visualise their spending habits

### Development Milestones

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
Features were developed over two sprints, each sprint lasting two weeks.
</div>

#### Milestone v1.2

**Track Finances**

- Add transaction
- Delete transaction
- Clear all transaction
- Edit transaction
- Find transaction(s)
- Get total expenditure
 
**Categorize Finances**

This feature serves as an extension to adding a transaction as categories provide finer details about a transaction.

Using the `edit_transaction` command, users can now:

- Add categories
- Edit existing categories
- Remove existing categories

**Others**

- Support file storage of transactions
- Remove all dependencies to original AB3
- Clean up package structure
- Configure Gradle + Github Actions for UI testing and coverage reporting

#### Milestone v1.3

- Support multi-format date time strings
- Expand search functionality of `find` command
- Add year support when retrieving total expenditure
- Summary visualizations for expenses
- UI improvements
- Support file storage of budget

#### Milestone v1.4

The bulk of this milestone was improving the documentation, ensuring clarity for readers. Such changes include:

- Adding detailed documentation about argument and prefix constraints
- Provide documentation on logic, model, and storage components


---

## Design Overview

### Architecture

<img src="images/ArchitectureDiagram.png">

The architecture for UniCa$h leverages the existing architecture from AB3 and extends several components like supporting storage of transactions and the budget.

### UI Component

UniCa$h is a Graphical User Interface (GUI) application, built using JavaFX. The following section describes the
implementation of the various UI components in UniCa$h.

#### UI Components
UniCa$sh consists of the following UI components:
1. Main Window
2. Help Window
3. Statistics / Budget Window

<img src="images/unicash/UiClassDiagram.png" width="404" />

In this section, we will be going through the implementation of the main window.

#### Main Window Design
The main window consists of three components: the command input box, as well as two panes,
the transaction list pane and the command results pane.

For the command input box, we have a custom `CommandBox` class that extends `TextField` to provide
the user with the ability to type their commands into the application.

For the transaction list pane, we have a custom `TransactionListPanel` class that is a `ListView` to
provide the user with a scrollable list of transactions. This view is updated whenever the user executes
commands that modify the transaction list. (i.e. `add`, `delete`, `edit`, `clear`)

For the command results pane, we have a custom `ResultDisplay` class. This pane displays the results of the
user's command execution. This view is updated whenever the user executes commands that modify the transaction.
Error messages are also displayed here, such as when the user enters an invalid command.

#### Main Window Implementation
The main window for UniCa$h is implemented using the `MainWindow` class. This class is made up of:
1. `CommandBox`: The command input box
2. `TransactionListPanel`: The transaction list pane
3. `ResultDisplay`: The command results pane

All these components, including the `MainWindow`, inherit from the abstract `UiPart` class which provides
the basic functionality of a UI component, such as the ability to set the root node of the component.

The `UI` component uses the JavaFx UI framework to render the UI components. The layout of these parts are defined
in the matching .fxml files that are located in `src/main/resources/view` folder. (e.g. The layout of the
TransactionCard is specified in`TransactionCard.fxml`)

The `UI` component is also responsible for:
- executing commands using the `Logic` component, through the `executeCommand` method
- listening for changes to the `Model` component, through the `listenToModelChanges` method

### Logic Component

**API** : [`Logic.java`](https://github.com/AY2324S1-CS2103-T16-3/tp/blob/master/src/main/java/unicash/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/unicash/LogicClassDiagram.png" width="500">

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/unicash/DeleteTransactionSequenceDiagram.png)

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
**Note:** The lifeline for `DeleteTransactionCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `UniCashParser` object which in turn creates a parser that matches the command if necessary (e.g., `AddTransactionCommandParser`) and uses it to parse the command.
   1. Note that not all commands would create an additional parser such as `GetBudgetCommand`, `ClearBudgetCommand`, etc.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddTransactionCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/unicash/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `UniCashParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddTransactionCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddTransactionCommand`) which the `UniCashParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddTransactionCommandParser`, `DeleteTransactionCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.
* Note that there is a dependency drawn between `UniCashParser` and `XYZCommand` as not all commands require the use of a specialised parser class.

### Model Component

**API** : [`Model.java`](https://github.com/AY2324S1-CS2103-T16-3/tp/blob/master/src/main/java/unicash/model/Model.java)

<img src="images/unicash/ModelClassDiagram.png" width="500">

The `Model` component,

* stores UniCa$h data i.e., all `Transaction` objects (which are contained in a `TransactionList` object), the `Budget` object and `Category` objects (which are contained in a `UniqueCategoryList` object).
* stores the currently 'selected' `Transaction` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Transaction>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
  * Refer [here](https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm) for a guide on Javafx bindings.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


### Storage Component

<img src="images/StorageClassDiagram.png" width="500">

UniCa$h stores any persistent data as local [JSON files](https://www.json.org/json-en.html), located in the same folder where the JAR file is run.

The following are the files that are created:

- `config.json`
- `preferences.json`
- `data/unicash.json`

[Jackson](https://github.com/FasterXML/jackson) is used for JSON (de)serialization and it relies on adapted models similar to the [Data Transfer Object pattern.](https://www.baeldung.com/java-dto-pattern)


#### Malformed JSONs

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
If a JSON file is malformed, the contents will not be loaded and a log message will be printed. 
<br><br>
However, no proactive measures are applied to rectify the issue such as automatically deleting the file or fixing the error to avoid prescribing a fixed approach to resolving malformed JSONs.
</div>

While we do not make assumptions about the intended action when encountering a malformed JSON file, most JSON files will be overwritten at some point, so the malformed JSON should eventually be overwritten with proper JSON.

For `data/unicash.json`, this occurs when adding, editing, or deleting the transaction list or updating the budget.

For `config.json` and `preferences.json`, this occurs in the `MainApp#initConfig` and `MainApp#initPrefs` methods.

### Continuous Integration (CI)

Continuous integration consists of the following:

1. General unit testing
2. UI testing
3. Automated testing on Github
4. Code coverage reporting

<div class="callout callout-info" markdown="span">
**[Github Actions](https://docs.github.com/en/actions) Primer**
<br><br>
Github Actions is used to execute a set of behavior on a repository and is often used for Continuous Integration and Continuous Deployment.
<br><br>
Github Actions are created as [YAML configuration files](https://yaml.org/) found in the `.github/workflows` folder.
<br><br>
GitHub Actions can be generally broken down into the following components:
<br>
1. Trigger: dictates when the action is run
<br>
2. Strategy & matrix: specifies the platform (OS) and any relevant versions to run the steps
<br>
3. Steps: consists of individual steps that can use other Github Actions to perform a set of actions in sequential order
</div>

#### General unit testing

General unit tests cover any non-UI related aspect of UniCa$h such as models, commands, and utility.

[JUnit 5](https://junit.org/junit5/), along with custom-built assertion methods like `CommandTestUtil#assertCommandSuccess`, are used to perform general unit testing.

#### UI testing

UI testing helps automate some manual testing by simulating user interaction with the UI, ensuring that the UI is responding as intended.

[JUnit 5](https://junit.org/junit5/) and [TestFX](https://github.com/TestFX/TestFX) are used to setup and perform UI testing.

To initialize a test class to work with TestFX, annotate it with the following:

```java
@ExtendWith(ApplicationExtension.class)
public class HelpWindowUiTest {
```

This leverages [JUnit 5's built-in extensions system](https://junit.org/junit5/docs/current/user-guide/#extensions) to
inject an `FxRobot` argument in each unit test. 

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
`FxRobot` is used as a driver to perform UI operations on the running UI, such as performing clicks, entering text, and performing keyboard inputs, along with searching for UI elements by `fx:id`.
</div>

```java
@Test
public void userInput_help_showHelpWindowAsRoot(FxRobot robot) throws TimeoutException {
    // Search for elements in the UI
    var beforeHelpContainer = robot.lookup("#helpMessageContainer").tryQuery();
    
    assertTrue(beforeHelpContainer.isEmpty());
    // Perform a mouse click
    robot.clickOn("#commandBoxPlaceholder");
    
    // Enter text input
    robot.write("help");
    
    // Perform a keyboard input
    robot.press(KeyCode.ENTER);
    
    var afterHelp = robot.lookup("#helpMessageContainer").tryQuery();
    assertTrue(afterHelp.isPresent());
}
```

Furthermore, there are two methods of initializing the UI for testing, the first using the `@Start` annotation and the other uses the `@BeforeEach` annotation.

##### Using `@Start`

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
Use `@Start` when testing individual UI components.
</div>

When defining a `@Start` method, a `Stage` is injected through
the [test runner](https://junit.org/junit5/docs/current/user-guide/#writing-tests-dependency-injection) and this
allows you to initialize a new `Stage` with custom UI components.

This is especially useful when working with individual UI components like `HelpWindow` and `TransactionCard` as it
provides a medium to render these elements without running the entire UI.

```java
@Start
public void start(Stage stage) {
    // helpWindow is declared outside of the method to be accessed in all unit tests
    helpWindow = new HelpWindow(stage);
    stage.show();
}
```

<div class="callout callout-info" markdown="span">
The `@Start` method is run before each test case.
</div>

##### Using `@BeforeEach`

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
Use `@BeforeEach` when testing the entire UI, i.e. integration testing.
</div>

When performing a general set of integration tests across the entire UI (like simulating user input to execution to
view the resulting UI changes), it is best to define a `@BeforeEach` method that uses `FxToolkit` to setup
the application (in this case, `MainApp`) with any given setup parameters like a default storage location.

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
When testing the entire application, a temporary storage file should be defined and provided for `MainApp` to avoid directly modifying the save data on your local machine.
</div>

```java
@TempDir
Path tempDir;

@BeforeEach
public void runAppToTests() throws TimeoutException {
    FxToolkit.registerPrimaryStage();
    FxToolkit.setupApplication(() -> new MainApp(tempDir.resolve("ui_data.json")));
    FxToolkit.showStage();
    WaitForAsyncUtils.waitForFxEvents(20);
}
```

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The `@BeforeEach` method is run before each test case.
</div>

It is also good convention to include an `@AfterEach` method to clean up the stages created during `@BeforeEach` so
that all resources are freed after every unit test:

```java
@AfterEach
public void stopApp() throws TimeoutException{
    FxToolkit.cleanupStages();
}
```

#### Automated testing on Github

Automated testing is achieved via the `.github/workflows/unit_test.yml` action.

Automated testing is triggered on every push and pull request and is run across all three major OSes: Ubuntu, MacOS, and Windows, and comprise of the following steps:

<img src="images/unicash/ci/AutomatedTestingActivityDiagram.png" width="368" />

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
UI tests are only run on Windows as both Linux and MacOS requires headless UI testing which is not well supported with Github Actions.
</div>

#### Code coverage reporting

Code coverage is generated using Github Actions and Gradle and uploaded to [Codecov.](https://app.codecov.io/gh/AY2324S1-CS2103-T16-3/tp)

Code coverage includes both general unit tests and UI tests, and reporting is achieved through the `.github/workflows/gradle.yml` action.

Similar to automated testing, code coverage reporting is triggered on every push and pull request and is run across all three major OSes.

<img src="images/unicash/ci/CodeCoverageReportingActivityDiagram.png" width="368" />

To ensure that code coverage reporting includes both general unit tests and UI tests, the following changes have been made to `build.gradle`:

1. A new Gradle task `uiTest` was created to only run UI tests that are unit test files that end with `UiTest`
2. The default `test` task is configured to exclude such files
3. The `jacocoTestReport` task is modified to only depend on (i.e. run before) the `uiTest` task if the system's OS is not MacOS, Ubuntu or *nux (i.e. Windows only)
4. The `coverage` task includes every `*.exec` file generated from both `uiTest` and `test` so that both coverage reports are available to Codecov

The Github Action for reporting the code coverage only uploads the coverage reports to Codecov if the runner is Windows as only then will there be a complete code coverage report.

These changes work around the limitation of Linux and MacOS runners on Github Actions not supporting a headless environment.

By introducing UI testing into the code coverage reporting, we have been able to achieve a code coverage of > 90%!

### General Classes and Components

#### StyleSheet

#### Command Usage Message Builder

Uniformly generates `MESSAGE_USAGE` strings to be used per command. Works well with `ExampleGenerator` to create consistent examples for every command using each type of prefix. Reduces the overhead of ensuring that all example values are accurate or ensuring that formatting for command usage is uniform.

Relies heavily on the [builder design pattern.](https://refactoring.guru/design-patterns/builder)

Example usage of `CommandUsage` would be:

```java
private static final MESSAGE_USAGE = new CommandUsage.Builder()
    .setCommandWord("set_budget")
    .setDescription("Sets the user's budget on UniCa$h.")
    .addParameter(PREFIX_AMOUNT, "Amount")
    .addParameter(PREFIX_INTERVAL, "Interval") 
    .setExample(
        ExampleGenerator.generate(
            "set_budget", 
            PREFIX_AMOUNT, 
            PREFIX_INTERVAL
        )
    ) 
    .build()
    .toString();
```

---

## Features & Implementation

UniCa$h comprises four key components:

1. [Transaction Management](#transaction-management)
2. [Budget Management](#budget-management)
3. [General Utility](#general-utility)
4. [User Interface](#user-interface)

### Transaction Management

#### Transaction Model

<img src="images/unicash/TransactionClassDiagram.png" width="700" />

UniCa$h tracks transactions with the use of `TransactionList` and `Transaction`. `TransactionList` acts
as a wrapper for a list of `Transaction` that enforces no `null`. `TransactionList` does not enforce any equality
constraints, thus any forms of duplicated `Transaction` are allowed to be stored. This was an intentional decision, 
as we want to allow users to record recurring transactions.

The `Transaction` class is composed of the following fields

1. `Name`: The name of the transaction.
2. `Type`: The transaction type of the transaction. UniCash supports expense and income only.
   1. `TransactionType`: An enum comprising of the values `income` and `expense`.
3. `Amount`: The monetary value of the transaction.
4. `DateTime`: The date and time of the transaction to be recorded.
5. `Location`: The location where the transaction took place.
6. `UniqueCategoryList`: A list of categories to be tagged with the transaction.
   1. `Category`: The name of a category tagged to a transaction.

The following are some noteworthy points regarding the attributes
1. There are 3 compulsory fields, namely `Name`, `Type` and `Amount`. The remaining fields would fall back to a default value if not specified.
2. There is a character limit for `Name` and `Location` set at up to 500 characters.
3. `Amount` entered has to be positive for both `income` and `expense`.
4. `Amount` is automatically rounded to 2 decimal places.
5. `UniqueCategoryList` enforces a unique (case-insensitive) constraint on `Category` it stores.
6. `UniqueCategoryList` enforces a max size of 5 `Category`
7. There is a character limit for `Category` set at up to 15 characters/
8. There is a limit of 100,000 transactions you can add to UniCa$h.

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
For more details on the constraints of each property of `Transaction`, refer to the [prefix types section in the user guide.](UserGuide.html#prefix-types)
</div>

#### Add Transaction

##### Overview

The `add_transaction` command adds a new `Transaction` to the `TransactionList` in UniCash.

The activity diagram of adding a Transaction is as shown below

<img src="images/unicash/AddTransactionActivityDiagram.png" width="500" />

The following sequence diagram shows how the different components of UniCash interact with each other

<img src="images/unicash/AddTransactionSequenceDiagram.png" width="2000" />

The above sequence diagram omits details on the creation of the attributes of a `Transaction` such as
`Name`, `Type` and `Amount` as it would make the diagram cluttered and difficult to read without adding
additional value.

ℹ️ **Note:** The lifeline for `AddTransactionCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML,
the lifeline reaches the end of diagram.

##### Details

1. The user specifies the transaction to be added by stating the name, amount, transaction type as well as any other optional fields.
2. The input will be parsed by `AddCommandTransactionParser`, and if it is invalid, `ParserException` is thrown, prompting for the user to enter again.
3. If the input is valid, a `Transaction` object is created and passed into the `AddTransactionCommand` to be executed by the `LogicManager`.
4. The `LogicManager` will then invoke the execute command, adding the `Transaction` to UniCash.

Note that only the `Category` field is allowed to be specified multiple times, while the other fields can only be specified once, else
a `ParserException` is thrown. Another noteworthy point is that `Category` that are added are to be case-insensitively unique and can only be up to
a specified value of 5 in the `UniqueCategoryList` class. Else, a `ParserException` would be thrown.

#### Get Total Expenditure

##### Overview

The `get_total_expenditure` command returns the total expenditure across a given month among all `expense` transactions in UniCa$h, with optional filters for a given category and year.

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The optional year value defaults to the current year if not provided.
</div>

The activity diagram of getting the total expenditure is as shown below

<img src="images/unicash/get-total-expenditure/GetTotalExpenditureActivityDiagram.png" width="1026" />

The following sequence diagram shows how the different components of UniCash interact with each other

<img src="images/unicash/get-total-expenditure/GetTotalExpenditureSequenceDiagram.png" width="1955" />

The above sequence diagram omits details on the creation of the arguments of a `GetTotalExpenditureCommand` such as
`Category` as it would make the diagram cluttered and difficult to read without adding additional value. It also omits
the specific `predicate` behavior of provided to perform the filtering.

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The lifeline for `GetTotalExpenditureCommandParser` should end at the destroy marker (X) but due to a
limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

##### Details

1. The user specifies the month to retrieve the total expenditure and the optional category
2. The input will be parsed by `GetTotalExpenditureCommandParser`, and if it is invalid, `ParserException` is thrown,
   prompting for the user to enter again
3. If the input is valid, a `GetTotalExpenditureCommand` object is created to be executed by the `LogicManager`, since no year is provided, the current year is inferred
4. The `LogicManager` will then invoke the `execute` method of the command, filtering the existing transaction list to only include `expense` type transactions that fall in the given month and category (if any)
5. The `GetTotalExpenditureCommand` also calculates the total expenditure from this filtered list of transactions

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
**Key constraints:**
<br><br>
The month to search is one-indexed, so it ranges from `[1, 12]`
<br>
The year has to be `>= 1920`
<br>
The category is a single filter that is matched in a case-insensitive manner
</div>

#### Delete Transaction

##### Overview

The `DeleteCommand` function deletes an existing `Transaction` from `TransactionList` in UniCash.

The activity diagram of deleting a Transaction is as shown below

<img src="images/unicash/DeleteTransactionActivityDiagram.png" width="600" />

The following sequence diagram shows the interaction between different components of UniCash.

<img src="images/unicash/DeleteTransactionSequenceDiagram.png" width="1200" />

The above sequence diagram omits details on the filtering of `TransactionList` and assumes that
the displayed `TransactionList` is showing all transactions. However, the logic of the `DeleteCommand`
remains the same for all list deletion.

ℹ️ **Note:** The lifeline for `DeleteTransactionCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML,
the lifeline reaches the end of diagram.

##### Details

1. The user specifies the transaction to be deleted by stating the integer index of the transaction to be deleted.
2. The input will be parsed by `DeleteTransactionCommandParser` and if the provided input is invalid, `ParseException` will be thrown,
   and the user is prompted to enter the command again with the correct input.
3. If the input is valid, an `Index` object is created with the given input integer, and passed into `DeleteTransactionCommand` to be executed
   by `LogicManager`
4. `LogicManger` will invoke the `execute` method of `DeleteTransactionCommand` which will delete the `Transaction` from UniCash.

It is important to take note that when the user input is parsed, it is based on the currently displayed `TransactionList` inside
`TransactionListPanel`. This means that even if a `TransactionList` contains `10` transactions, given a specific nominally valid
number like `7`, it can still throw a `ParseException` if the shown `TransactionList` contains less than `7` items. This feature is
intentional, as the User is able to, for example, `find` a particular group of transaction and immediately delete those transactions
by just looking at their displayed index number without having to refer to an external identifier of that transaction. UniCash will
automatically handle the visual ordering and representation of transactions with the `TransactionsListPanel` in the UI. The details
and diagrams for this part will be elaborated further in the UI section (and other relevant sections) of this Developer Guide.

#### Edit Transaction

##### Overview

The `edit_transaction` command edits an existing `Transaction` from the `TransactionList` in UniCa$h.

The following sequence diagram shows how the different components of UniCa$h interact with each other. It is further explained in the **Details** section below.

<img src="images/unicash/EditTransactionSequenceDiagram.png" width="1200" />


##### Details

1. The user specifies the transaction to be edited by first stating the (one-based) index of the transaction they want to edit. This
   is followed by listing the fields and updated values that they would like to edit.
   (E.g. `edit_transaction 1 n/Food Clique c/food`)
2. The user input with the requested edits will be parsed by `EditCommandTransactionParser`, and if it is invalid, `ParserException` is thrown, prompting for the user to enter again. It is important to note that at this point, a valid index is any integer that is 1 or greater.
3. If the input is valid, the `EditCommandTransactionParser` creates an `EditTransactionDescriptor` object, which contains the edits which the user wishes to make to the transaction.
4. An `EditTransactionCommand` is constructed with the one-based index of the transaction to edit and the `EditTransactionDescriptor` object. This `EditTransactionCommand` is then returned by the `EditCommandTransactionParser`
5. The `LogicManager` will then invoke the execute command of the `EditTransactionCommand`, editing the `Transaction` in UniCa$h.
6. The `EditTransactionCommand#execute` method then first checks if the one-based index is too large (i.e. if there are only `n` transactions, but the index provided is greater than `n`). If this is true, then a `CommandException` is thrown,
   prompting the user to input an index that is at most, the number of transactions available. If the `CommandException` is not thrown at the step above, then the execution proceeds.
7. The `EditTransactionCommand#execute` method then calls the `createEditedTransaction` method, which creates a new `Transaction` object with the updated values after editing. Note that this `Transaction` object is a different object from what is stored in UniCa$h.
8. The `EditTransactionCommand#execute` method then updates UniCa$h with the new `Transaction` using the `Model#setTransaction` method.

Note that although all fields can be edited, the `Name`, `Amount`, and `Type` fields cannot be left blank. The constraints laid
out in the **Add Transaction** section above also remain.

### Budget Management

<div class="callout callout-important" markdown="span">
For this team project, we have opted to simplify the budgeting feature by limiting the user to a single budget at a time that can be configured for different intervals and amounts.
</div>

#### Budget Model

<img src="images/unicash/BudgetClassDiagram.png" width="250" />

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The budget is stored in `data/unicash.json`, much like the transactions. Refer to the [storage component](#storage-component) for more details.
</div>

UniCa$h tracks a user's budget with the use of `Budget`.

The `Budget` class is composed of the following fields

1. `Amount`: The amount allocated to the budget.
2. `Interval`: The budget interval of the transaction.
   1. `BudgetInterval`: An enum consisting of the values `day`, `week` and `month`.

The following are some noteworthy points regarding the attributes
1. `Amount` here follows the same constraints as the one mentioned in the `Transaction`'s `Amount` class.

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
For more details on the constraints of each property of `Budget`, refer to the [prefix types section in the user guide.](UserGuide.html#prefix-types)
</div>

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The [prefix types section in the user guide](UserGuide.html#prefix-types) contains a brief about how intervals are handled.
<br><br>
Expenses that fall within the interval are included and the total expense is computed relative to the budget.
<br>
1. Daily: expenses that occur within the same **day of year** (i.e. the expenses that occur on day `x` are included if today is day `x`, expenses on day `x +/- 1` are not includued)
<br>
2. Weekly: expenses that occur within the same **week of year**. This is dependent on the current year, for more information refer to the [documentation here.](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/temporal/WeekFields.html#weekOfYear())
<br>
3. Monthly: expenses that occur within the same **month** (i.e. the expenses that occur in September are included if today falls under September)
</div>

#### Set Budget

**Overview**

The `set_budget` command sets a user defined budget globally across UniCa$h.

The activity diagram of getting the total expenditure is as shown below

<img src="images/unicash/budget-management/SetBudgetActivityDiagram.png" width="450" />

The following sequence diagram shows how the different components of UniCash interact with each other

<img src="images/unicash/budget-management/SetBudgetSequenceDiagram.png" width="1622" />

The above sequence diagram omits details on the creation of the arguments of a `SetBudgetCommand` such as
`Amount` and `Interval` as it would make the diagram cluttered and difficult to read without adding additional value. 

It also omits the file saving aspect of this, where the updated budget is saved to the `data/unicash.json` file.

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The lifeline for `SetBudgetCommandParser` should end at the destroy marker (X) but due to a
limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

**Details**

1. The user specifies amount and interval that the budget will operate on
2. The input will be parsed by `SetBudgetCommandParser`, and if it is invalid, `ParserException` is thrown,
   prompting for the user to enter again
3. If the input is valid, a `SetBudgetCommand` object is created to be executed by the `LogicManager`
4. The `LogicManager` will then invoke the `execute` method of the command, setting the budget in the `ModelManager`

#### Clear Budget

**Overview**

The `clear_budget` command removes the globally set UniCa$h budget. If no budget is present, the user is prompted to set one using `set_budget` instead.

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
`clear_budget` will not parse any additional argument or parameters. Even if additional argument or parameters are given, any existing budget will be cleared regardless, without any additional effects.
</div>

The following sequence diagram shows how the different components of UniCash interact with each other

<img src="images/unicash/budget-management/ClearBudgetSequenceDiagram.png" width="1171" />

This sequence diagram omits the file saving aspect of the command, where `data/unicash.json` is updated to now hold a `null` budget value.

**Details**

1. The user runs the clear budget command
2. The command will be parsed by `UniCashParser` and a `ClearBudgetCommand` object is created and executed by the `LogicManager`
3. The `LogicManager` will then invoke the `execute` method of the command
4. The command will check if UniCa$h currently contains an existing budget, if it does not, a "no budget" message is returned, otherwise, the existing budget is cleared and a success message is returned instead

#### Get Budget

**Overview**

The `get_budget` command computes the total expenditure relative to the existing budget within the given interval. If no budget is present, the user is prompted to set one using `set_budget` instead.

Intervals work by filtering by the specified time period:
- For `day` intervals, only transactions of the same day are found
- For `week` intervals, only transactions of the same [week of year](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/temporal/WeekFields.html#weekOfYear()) are found
- For `month` intervals, only transactions of the same month are found

<div class="callout callout-important" markdown="span" style="margin-bottom: 20px;">
`get_budget` will not parse any additional argument or parameters. Even if additional argument or parameters are given, no additional effects will be triggered.
</div>

The following sequence diagram shows how the different components of UniCash interact with each other

<img src="images/unicash/budget-management/GetBudgetSequenceDiagram.png" width="1163" />

The above sequence diagram omits details such as internal method calls to `GetBudgetCommand#getIntervalFilter` and `GetBudgetCommand#getIntervalString` to reduce clutter.

**Details**

1. The user runs the get budget command
2. The command will be parsed by `UniCashParser` and a `GetBudgetCommand` object is created and executed by the `LogicManager`
3. The `LogicManager` will then invoke the `execute` method of the command
4. The command will check if UniCa$h currently contains an existing budget, if it does not, a "no budget" message is returned, otherwise, the budget remainder will be calculated
5. If the remainder amount is negative (`< 0.00`), then a negative amount message will be returned
6. Otherwise, a non-negative message will be returned

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
The only difference between the negative and non-negative messages is that the negative message places the `-` symbol before the `$` so `-$xx.xx` whereas the non-negative message does not contain the `-` symbol
</div>

### General Utility

#### Clear Transactions

##### Overview

The `ClearTransactionsCommand` deletes all existing `Transactions` from `TransactionList` in UniCash.

The activity diagram of clearing all transactions is as shown below

<img src="images/unicash/ClearTransactionsActivityDiagram.png" width="400" />

The following sequence diagram shows the interaction between different components of UniCash.

<img src="images/unicash/ClearTransactionsSequenceDiagram.png" width="800" />

**Note:** Given that `ClearTransactionsCommand` takes in no arguments, it does not have an associated Parser class
like the other `Command` classes. This is currently the case, however, given that the command entirely erases the
existing Unicash, a `ClearTransactionsCommandParser` is proposed to be implemented at a later date to ensure an
additional layer of safety for the User.

##### Details

1. The user inputs the command to reset unicash
2. A `ClearTransactionsCommand` object is created with no arguments.
3. `LogicManager` will invoke the `execute` method of `ClearTransactionsCommand`
   which will replace the existing `Model` property with a new `UniCash` object which
   would contain an empty `TransactionList`.

Here, it must be noted that unlike `DeleteTransactionCommand`, individual transactions in the `TransactionList`
are not deleted singularly. As opposed to iteratively deleting each transaction in the `TransactionList`, the more
efficient way to achieve the same effect would be to simply set the `Model` contained in `LogicManager` to an new
`UniCash` object, as the newly created `UniCash` object would now have an empty `TransactionList` encapsulated within.
This emulates the iterative deletion of all transactions in the `TransactionList`.

#### Help 

##### Overview

The `help` command can be used in 2 different ways.

1. `help` with no arguments.
2. `help COMMAND_WORD` with 1 argument containing a command word.

##### Details

1. `help`

Opens a Help Window with a link to our User Guide and displays a help message on the right panel containing a list of all our commands.

2. `help add_transaction`

A help message specific to the command word on the right panel. The message will contain
information on that command and provide instructions on how to run that command.

#### Exit

`exit`

This command will exit UniCa$h.

### User Interface

#### Summary Statistics

[//]: # (#### etc)

### Minor Features

#### Hash-based coloring

---
## Requirements

### Non-Functional Requirements

1. The application should be cross-platform functional on major operating systems, i.e. Windows, MacOS, Linux, running at least Java 11
2. All transactions and budget managed by the user should be saved and backed up locally and restored the next session as long as the data has not been corrupted
3. The user guide should be clear and straightforward so that the user can perform all major tasks intuitively
4. All non-private methods should be well-documented to ensure that the codebase is maintainable
5. The application should have an intuitive interface with easy navigation commands and clear instructions/errors
6. The application achieves at least 85% in test coverage with comprehensive unit and integration tests
7. The user should not experience excessive sluggishness while using the application
8. The application should serve the latest updates of transactions the moment these updates are confirmed
9. The application should not require an active Internet connection to function
10. The application should be able to generate visualisations for 1000 expenses without any excessive sluggishness

### User Stories

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
Priorities for user stories are marked as follows: <br>
High (must have) - `* * *` <br>
Medium (nice to have) - `* *` <br>
Low (unlikely to have) - `*`
</div>

| Priority | As a …​                | I want to …​                                                        | So that I can…​                                                                                      |
|----------|------------------------|---------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| `* * *`  | tech-savvy NUS student | add a new transaction                                               | track my cash flow                                                                                   |
| `* * *`  | tech-savvy NUS student | retrieve a list of all my transactions with all transaction details | check if I have forgotten to log some transactions                                                   |
| `* * *`  | tech-savvy NUS student | get a transaction                                                   | locate details of a transaction without having to go through the entire list                         |
| `* * *`  | tech-savvy NUS student | delete transactions                                                 | remove transactions I no longer need                                                                 |
| `* * *`  | tech-savvy NUS student | edit transactions that have been created                            | rectify incorrect inputs in my transactions                                                          |
| `* * *`  | tech-savvy NUS student | view my total expenditure                                           | see at a glance how much I have spent in total without having to manually tabulate                   |
| `* * *`  | tech-savvy NUS student | find transaction(s) by name, location and/or category               | locate specific transactions and its associated details without having to go through the entire list |
| `* * *`  | tech-savvy NUS student | view my total expenditure by month/year and filter by category      | check my expenditure in specific areas without having to manually tabulate                           |
| `* * *`  | tech-savvy NUS student | clear all my transactions                                           | start afresh with no transaction history                                                             |
| `* *`    | tech-savvy NUS student | set a daily/weekly/monthly budget                                   | spend within my limits                                                                               |
| `* *`    | tech-savvy NUS student | check the budget that I set                                         | the spending limit I set for myself                                                                  |
| `* *`    | tech-savvy NUS student | check my remaining budget                                           | see at a glance if I am spending within limits                                                       |
| `* *`    | tech-savvy NUS student | clear my current budget                                             | start afresh with no budget set                                                                      |
| `* *`    | tech-savvy NUS student | view a graphical summary of my transactions                         | see my cash flow at a glance                                                                         |
| `* *`    | tech-savvy NUS student | input different DateTime formats in my transactions                 | use the application with ease                                                                        |
| `* *`    | tech-savvy NUS student | use command keyword abbreviations                                   | use the application efficiently                                                                      |
| `* * *`  | tech-savvy NUS student | see usage instructions                                              | refer to instructions when I forget how to the App                                                   |
| `* *`    | tech-savvy NUS student | see usage instructions for a command                                | refer to instructions when I forget how to the command                                               |
| `* * *`  | tech-savvy NUS student | reset the application                                               | restore it to the original state                                                                     |
| `* * *`  | tech-savvy NUS student | exit the application                                                | close the application                                                                                |
| `*`      | tech-savvy NUS student | import my transaction data from a spreadsheet to the app            | input all my historical expenses without having to manually input them one at a time                 |
| `*`      | tech-savvy NUS student | export my transaction data to a spreadsheet                         | backup my transaction data                                                                           |
| `*`      | tech-savvy NUS student | fix a ratio to be set aside from my excess funds every month        | still have a portion of my savings even when I make big purchases                                    |
| `*`      | tech-savvy NUS student | add transactions that are due on a recurring basis                  | include recurring expenditures without having to manually input them every time they occur           |
| `*`      | tech-savvy NUS student | receive autofill recommendations based on my historical inputs      | save time typing the full command and reduce the chance of making a mistake                          |



### Use Cases
The following documents use cases for our application

For the following Use Cases (unless specified otherwise):
- The System is `UniCa$h`
- The Actor is `User`

**Use Case: UC01 - Adding a transaction**

**MSS:**
1. User enters the command to add a transaction with the correct format.
2. User submits the request.
3. UniCa$h adds the transaction to the transactions list and displays success message.

   Use Case ends

**Extensions**
- 2a. User enters an incorrect format
   - 2a1. UniCa$h displays an error message with the correct command format.
   - Use case resumes at step 1.
- 2b. User enters an extremely high number
   - 2b1. UniCa$h prompts the user with a warning of the input
   - 2b2. User confirms the request
   - Use case resumes at step 3.

**Use Case: UC02 - Finding a transaction**

**MSS:**
1. User enters the command to find a transaction with the correct format.
2. User submits the request.
3. UniCa$h filters all the transactions based on the specified filters and returns
   the filtered transaction with a success message.

   Use Case ends

**Extensions**
- 2a. User enters an incorrect format.
   - 1a1. UniCa$h displays an error message with the correct command format.
   - Use case resumes at step 1.

- 3a. UniCa$h does not find any results matching the filter.
   - 3a1. UniCa$h displays a message saying no results found.
   - Use Case resumes at step 1

**Use Case: UC03 - Delete a transaction**

**MSS:**
1. User enters the command to delete a transaction with the correct format.
2. User submits the request.
3. UniCa$h finds the transaction based on the passed in arguments.
4. UniCa$h deletes the transaction.
5. UniCa$h displays success message.

   Use Case ends

**Extensions**
- 2a. User enters an incorrect format.
   - 2a1. UniCa$h displays an error message with the correct command format.
   - Use case resumes at step 1.

- 3a. UniCa$h does not find any transactions matching the provided arguments.
   - 3a1. UniCa$h displays a message saying no results found.
   - Use Case resumes at step 1


**Use Case: UC04 - Listing All Transactions**

**MSS:**
1. User enters the command to list all transactions with the correct format (i.e. no parameters).
2. User submits the request.
3. UniCa$h retrieves the list of all transactions and displays them for the User.

   Use Case ends.

**Extensions**
- 2a. User enters the command with the incorrect format (i.e. with parameters).
   - 2a1. UniCa$h displays an error message, requests for the correct format.
   - Use case resumes from step 1.

- 3a. There are no transactions for UniCa$h to retrieve.
   - 3a1. UniCa$h displays no transactions, and informs the user that all transactions have been retrieved.
   - Use Case ends.

**Use Case: UC05 - Finding a Transaction**

**MSS:**
1. User enters the command to find transaction with the correct format.
2. User submits the request.
3. UniCa$h filters the transactions based on the specified filters and returns the filtered list of expenses with a success message.

   Use Case ends.

**Extensions**
- 2a. User enters an incorrect format.
   - 2a1. UniCa$h displays an error message, requests for correct format.
   - 2a2. User enters command with new format.
     Steps 2a1-2a2 are repeated until the format entered is correct.

  Use case resumes from Step 3.

- 3a. UniCa$h does not find any results matching the filter.
   - 3a1. UniCa$h displays a message informing the user that no results were found.
   - Use Case ends.

**Use Case: UC06 - Get Total Expenditure**

**MSS:**
1. User enters the command to get the total expenditure.
2. User submits the request.
3. UniCa$h parses the command, and it is in the right format.
4. UniCa$h calculates the expenditure based on the parameters passed in.
5. UniCa$h displays the calculated expenditure.

   Use Case ends.

**Extensions**
- 3a. UniCa$h parses command, and detects that user entered the incorrect format (missing fields)
   - 2a1. UniCa$h displays an error message, requests for command to be re-entered.
   - Use case resumes at step 1

**Use Case: UC07 - Clear all transactions**

**MSS:**
1. User enters the command to clear all transactions with the correct format. (i.e. no parameters)
2. User submits the request.
3. UniCa$h deletes all transactions in the transactions list.
4. UniCa$h displays success message.

   Use Case ends

**Extensions**
- 2a. User enters an incorrect format.
   - 2a1. UniCa$h displays an error message with the correct command format.
   - Use case resumes at step 1.

- 3a. UniCa$h finds an empty transactions list.
   - 3a1. UniCa$h displays a message saying that transactions list is empty.
   - Use Case resumes at step 1

**Use Case: UC08 - Show UniCa$h Help**

**MSS:**
1. User enters the command to show help with the correct format. (i.e. no parameters)
2. User submits the request.
3. UniCa$h displays help message in the help window.

   Use Case ends

**Extensions**
- 2a. User enters an incorrect format.
   - 2a1. UniCa$h displays an error message with the correct command format.
   - Use case resumes at step 1.

**Use Case: UC09 - Exit UniCa$h**

**MSS:**
1. User enters the command to exit UniCa$h with the correct format (i.e. no parameters)
2. User submits the request.
3. UniCa$h displays exit message and application closes

   Use Case ends

**Extensions**
- 2a. User enters an incorrect format.
   - 2a1. UniCa$h displays an error message with the correct command format.
   - Use case resumes at step 1.

**Use Case: UC10 - Set Budget**

**MSS:**
1. User enters the command to set the budget.
2. User submits the request.
3. UniCa$h parses the command, and it is in the right format.
4. UniCa$h sets the global budget.
5. UniCa$h displays the new budget.

   Use Case ends.

**Extensions:**

- 3a. UniCa$h parses command, and detects that user entered the incorrect format (missing fields)
   - 3a1. UniCa$h displays an error message with the correct command format
   - Use case resumes at step 1.
- 4a. UniCa$h contains existing budget.
   - 4a1. UniCa$h replaces existing budget with new one.
   - Use case resumes at step 5.

**Use Case: UC11 - Clear Budget**

**MSS:**
1. User enters the command to clear the budget.
2. User submits the request.
3. UniCa$h clears the existing budget.
4. UniCa$h displays a success message.

   Use Case ends.

**Extensions:**

- 3a. UniCa$h does not contain an existing budget.
   - 3a1. UniCa$h displays a prompt to create a budget first.
   - Use case ends.

**Use Case: UC12 - Get Budget**

**MSS:**
1. User enters the command to get the budget.
2. User submits the request.
3. UniCa$h tabulates the expenditure relative to the budget set for the given interval.
4. UniCa$h displays the budget and the net amount of the budget.

   Use Case ends.

**Extensions:**

- 3a. UniCa$h does not contain an existing budget.
   - 3a1. UniCa$h displays a prompt to create a budget first.
   - Use case ends.


---
## Instructions for manual testing
### Launch and shutdown
#### Initial launch
1. Download the jar file (`unicash.jar`) and copy it into an empty folder
2. Open a terminal and navigate to the folder
3. Run `java --version`, ensure that you are running on Java 11. If not, download and install Java 11. 
4. Run `java -jar unicash.jar`
   * Expected output: A GUI window should appear with the title "UniCa$h", with a set of sample transactions.

### Transactions
#### Adding a transaction
Use `add n/Buying groceries type/expense amt/300 dt/18-08-2023 19:30 l/NTUC c/Food`

Expected output: Transaction is added and a success message is displayed.

#### Deleting a transaction
Use `delete 1`

Expected output: Transaction is deleted and a success message is displayed.

#### Editing a transaction
Use `edit 1 n/Buying groceries type/expense amt/300 dt/18-08-2023 19:30 l/NTUC c/Food`

Expected output: Transaction is edited and a success message is displayed.

#### Listing all transactions
Use `list`

Expected output: All transactions are displayed.

#### Finding transaction(s)
Use `find n/Buying groceries l/NTUC c/Food`

Expected output: All transactions that match the specified filters are displayed.

#### Get a transaction
Prerequisite: There is at least 1 transaction stored in UniCa$h.

Use `get 1`

Expected output: The retrieved transaction is displayed.

#### Clear all transactions
Use `clear_transactions`

Expected output: All transactions are cleared and a success message is displayed.

### Expenditure
#### Get total expenditure
Prerequisite: There is at least 1 transaction of category expense stored in UniCa$h.

Use `get_total_expenditure month/10 c/Food year/2006`

Expected output: The total expenditure is displayed.

### Statistics
#### Summary statistics
Use `summary`

Expected output: The summary statistics window pops up and displays the summary statistics.

### Budget
#### Set your budget
Use `set_budget amt/<amount> interval/<interval>`

Expected output: Budget is set and a success message is displayed.

#### Get your budget
Use `get_budget`

Expected output: Budget is displayed.

#### Clear your budget
Use `clear_budget`

Expected output: Budget is cleared and a success message is displayed.

### Utility
#### Reset
Use `reset_unicash`

Expected output: UniCa$h resets to default state and restores the sample transactions.
#### Help
Use `help`

Expected output: The help window pops up and displays the link to the User Guide. Message with all commands is displayed on the right panel.

Use `help <command word>`

Expected output: The help window pops up and displays the usage for the specified command.
#### Exit
Use `exit`

Expected: UniCa$h closes.

---
## Appendix
### Appendix A: Planned Enhancements

- The current `get_budget` command does not alter the transaction list when processing the expenses within the interval. This can lead to confusion as users may not be aware of the specific transactions that are being included in the calculation of the budget remainder, and as a result, mistaking the budget calculation as not working. 
 
    We plan to apply a filter to the transaction list, like in `get_total_expenditure`, when `get_budget` is run to accurately display the list of transactions that we have included for the calculation to reduce this potential user confusion.
- The current `get_budget`'s weekly interval calculation uses the week of year. However, given that this information is not immediately intuitive to users, users may not be aware of how weekly budgets are calculated, leading to confusion.

  We plan to modify `get_budget`'s weekly interval calculation to use the previous `6` days + today to make the command more intuitive for users.
- `find` to support all properties
- Add confirmation to some data deletion commands (`delete`, `clear_transactions`, `reset_unicash`)
- Expand summary window to incomes as well
- Batch deletion/clear (by day, by month, etc)

### Appendix B: Potential Features

Every user can...

- Divide a group spending and tag friends involved in the spending
- View a price list comparison of all available shops near NUS
- Set recurring transactions
- Support more than one global budget to allow users to set budgets for each category or different budgets for day/week/month
- Support more types of transaction such as `transfer`

### Appendix C: Effort

<div class="callout callout-info" markdown="span" style="margin-bottom: 20px;">
UniCa$h is based off of AB3's original architecture, leveraging the existing command handling framework and UI classes.
</div>

#### Migrating AB3 to UniCa$h

While we leverage the existing AB3 architecture, UniCa$h introduces two custom models: `Transaction` and `Budget`, both of which includes its own custom set of properties like `Name`, `Amount`, and `Interval`. These custom properties include new constraints that had to be thoroughly tested.

Although we were able to loosely reference the existing code from AB3 to build these custom models, the surrounding architecture such as `ModelManager`, `UniCash`, and `UniCashStorage` had to be altered to work with the new domain that UniCa$h introduces.

We had to alter `ModelManager` and `UniCash` to contain the relevant `Transaction` and `Budget` information. This involved adding additional methods to handle transaction and budget management. `UniCashStorage` was created to support persisting the transaction and budget data to a file. For this, we had to create `JsonAdaptedX` versions of the models like `Transaction`, `Budget`, and `Category` to ensure that the user's data would be safely written to and reliably read from the storage file (taking inspiration from AB3's `JsonAdaaptedPerson`).

Additionally, removing all dependencies to the old AB3 architecture such as the `Person` model or `JsonAdaptedPerson` was a big challenge as we had to ensure that doing so did not cause any regression to the new models and classes. However, it was also a good exercise in revealing unintended dependencies between UniCa$h and the old AB3 models and classes.

#### Improving the UI

[//]: # (TODO: Rubesh)

#### Providing Summary Statistics

[//]: # (TODO: Ernest)

#### Increasing Test Coverage

The original AB3 project had a severe lack of coverage of the UI components and this resulted in bugs that may be difficult to replicate and that could be easily missed when performing manual testing. By implementing [TestFX](https://github.com/TestFX/TestFX/blob/master/README.md) and modifying both Gradle and GitHub Actions, we were able to support UI testing. This raised our test coverage to over 90%! 

TestFX has allowed us to create test suites for both individual UI components and integration tests that reduce the need to perform lengthy manual testing to ensure key UI behavior, providing a peace of mind that critical UI features were working as intended.

### Appendix: Known Bugs

- Light scrollbars hard to see

### Links

- [User Stories](https://github.com/orgs/AY2324S1-CS2103-T16-3/projects/1/views/2)
- [Project Website Home](https://ay2324s1-cs2103-t16-3.github.io/tp/)
- [UniCa$h Demo](https://drive.google.com/file/d/1l2Erx-qMpM274AUePf6DDlvSl9G15y6T/view?usp=share_link)
