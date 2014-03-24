MinecraftSounds
===============

**Goal:** A medium-quality, multi-activity soundboard app featuring images and sounds from the popular game, Minecraft.

**init:** Upon cloning this repository, before the application is buildable, you must first run `sh init` 
This will initialize the application with system-dependent files and create a new [android avd](http://developer.android.com/tools/devices/index.html) for use in 
project developement, named android-10\_minecraft. This command is dependent upon having [java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [android](http://developer.android.com/sdk/index.html)
installed on your system, as well as having [downloaded the android 2.3.3 (android-10) API](http://developer.android.com/sdk/installing/adding-packages.html).

**installapp:** Android uses the ant build framework, which includes multiple commands to install the app. 
These are packaged in `installapp`. To install the application, run: `sh installapp`.
This command is dependent upon having [Ant](http://ant.apache.org/bindownload.cgi) installed on your system.

===============
