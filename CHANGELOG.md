Change Log
==========

Version 1.4.0 *(2021-0X-XX)*
----------------------------
* Improve: Shortbread is now an _isolating_ annotation processor, which improves the performance of incremental annotation proccessing.
Before, the processor was of type _aggregating_.
* Improve: Shorcut methods/functions don't have to be `public` anymore. Any visibility higher than `private` is enough.
* Fix: When using `R2`, resource values sometimes were not properly read when the incremental annotation processing was incremental


Version 1.3.0 *(2021-03-31)*
----------------------------
* New: Shortbread is now initialized automatically using [App Startup](https://developer.android.com/topic/libraries/app-startup)
* Deprecated: `Shortbread.create(context)` - no need to call this anymore as the shortcuts are set automatically during app startup
* Fix: Resource values sometimes were not properly read when the incremental annotation processing was incremental
* Improve: `ActivityLifecycleCallbacks` will not be registered if there are no method shortcuts


Version 1.2.0 *(2021-03-14)*
----------------------------
* New: Support for non-final resource IDs. See [README.md](https://github.com/MatthiasRobbers/shortbread#non-final-resource-ids)
for detailed usage instructions.
* Update: `androidx.annotation:annotation` to `1.1.0`
* Update: Android Gradle plugin to `4.1.2`
* Migrated publishing from JCenter to Maven Central


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
* Fix: Annotated methods are called before `onCreate()` ([#13](https://github.com/MatthiasRobbers/shortbread/issues/13))
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


Version 1.0.0 *(2017-02-11)*
-----------------------------
Initial release
