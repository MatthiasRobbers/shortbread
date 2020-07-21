Change Log
==========

Version 1.1.0 *(2020-07-21)*
-----------------------------
* New: Support for incremental annotation processing
* Switch from Support annotations library to `androidx.annotation`
* Java 8 is now required
* Minimum SDK increased from `9` to `14` (app shortcuts are still not available before `25`)
* Update: Android Gradle plugin to `3.6.4`
* Some small changes to bring everything to 2020


Version 1.0.2 *(2017-09-24)*
-----------------------------
* Fix: Annotated methods are called before `onCreate()` (#13)
* Update: Support annotations library to `26.0.2`. This requires the new Google Maven Repository:

  ```groovy
  google()
  ```
  or    
  ```groovy
  maven {
      url "https://maven.google.com"
  }
  ```


Version 1.0.1 *(2017-03-04)*
-----------------------------

* Fix: `Shortbread.create(context)` can now also be called if there are no `@Shortcut` annotations in the code, which before produced a crash. Previously created shortcuts are now removed.
* Fix: Internal `NullPointerException` when an activity containing method shortcuts is not launched via a method shortcut
* Add Javadoc for the public API


Version 10.2.0 *(2017-02-11)*
-----------------------------

Initial release
