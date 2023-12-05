# Project setup
- execute "git config --local core.hooksPath .githooks/" (enabling git hooks from the project)
- execute "chmod +x .githooks/pre-commit" (if mac os)
- during a commit via Android Studio, the run git hooks checkbox is always enabled

# Info
- the project is supported according to free time and desire
- add a minimum number of screens (to make it easier to maintain)
- git history is stored in a private repository

# Project Objective:
- familiarity in new recommended implementation methods
- in one place there was a small code that can be shown
- in one place there was a good skeleton of the project (if it is planned not small,
  developed by a team)

# Application logs (LogHelper.kt is a class for logging)
- application_lifecycle (you can view the application lifecycle)
- application_ui (some ui events)
- main_info (you can view basic information)
- StrictMode (information from the library about possible problems in the code)
- okhttp.OkHttpClient (view requests in logs)
- ActivityTaskManager (by the Displayed filter, you can see how long the cold start was performed)