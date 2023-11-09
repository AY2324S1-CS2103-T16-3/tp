---
layout: page
title: Jamie's Project Portfolio Page
---

### Project: UniCa$h

UniCa$h is a desktop application used for university students who want to be more financially conscious.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 19 kLoC.

Given below are my contributions to the project.

* **New Feature**: `Budget` class [[PR #103]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/103)
    * What it does: Provides the representation of a `Budget` in UniCa$h. Users are able to set either a weekly, monthly, or yearly budget.
    * Justification: Represents the `Budget` a user can set for themselves. It is a core feature of UniCa$h, and is used to calculate the amount of money a user has left to spend.
* **New Feature**: `Set Budget` command [[PR #103]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/103)
    * _Note: This was worked on with Jiahao, where he added the budget logic._
    * What it does: Adds a `Budget` to UniCa$h.
    * Justification: Allows users to set a `Budget` for themselves.
* **Refactored Feature**: `List` command [[PR #60]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/60)
    * What it does: Lists all the transactions in UniCa$h.
    * Justification: The `List` command was refactored from AB3's implementation to allow for the listing of all transaction fields.
    * Credits: AddressBook3's implementation of the `List` command
* **Refactored Feature**: `Find` command [[PR #69]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/69)
    * _Note: This command was enhanced in another PR by Rubesh_
    * What it does: Finds all the transactions in UniCa$h that match the given keyword.
    * Justification: The `Find` command was refactored from AB3's implementation to allow for the finding of all transaction fields.
    * Credits: AddressBook3's implementation of the `Find` command
* **Refactored Feature**: UniCa$h UI [[PR #76]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/76)
    * _Note: UI functionality was enhanced in a separate PR, by Rubesh_
    * What it does: Provides a GUI for UniCa$h. It was refactored from AB3's implementation, and enhanced further to match the prototype our team created (e.g. Layout).
    * Justification: The GUI inherited from AB3 was insufficient for our team's needs, and a better UI was required for users to have a smooth experience using UniCa$h. This refactoring serves as a base for future enhancements to the UI.
    * Credits: AddressBook3's implementation of the GUI

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=T16&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=jamz903&tabRepo=AY2324S1-CS2103-T16-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)


* **Project management**:
    * Managed releases `v1.2a` (1 release) on GitHub


* **Enhancements to existing features**:
    * Enhance Style of UI to match prototype [[PR #76]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/76)
    * Add support for user to input multiple DateTime formats [[PR #87]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/87)
    * Add support for multiple command shortcuts for the same command [[PR #133]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/133)


* **Other enhancements**:
    * Create UI Mockup [[PR #38]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/38)
    * Unify `COMMAND_WORD` and `MESSAGE_USAGE` through a common `CommandType` enum class [[PR #133]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/133)


* **Documentation**:
    * User Guide:
        * Remove AB3 content from the User Guide & replace it with UniCa$h content [[PR #43]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/43/files)
        * Add documentation for `List` and `Find` commands [[PR #51]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/51/files)
        * Add documentation for accepted alternative command words [[PR #149]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/149)
    
    * Developer Guide:
      * Add Use Cases for `List` and `Find` commands [[PR #56]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/56)
      * Added implementation details for the UI components [[PR #108]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/108)

* **Community**:
    * PRs reviewed (with non-trival review comments): [[PR #93]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/93), [[PR #130]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/130), [[PR #136]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/136), [[PR #141]](https://github.com/AY2324S1-CS2103-T16-3/tp/pull/141)
