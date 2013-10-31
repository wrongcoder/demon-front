README for Demon Front developers
===============================================================================


Before you begin
-------------------------------------------------------------------------------

1.	You must generate the TextureAtlas assets before running the project
	for the first time. To do this, run the `compile` phase of the build:

		demon_front$ mvn compile

		[INFO] --- exec-maven-plugin:1.2.1:java (pack-textures) @ demon_front-core ---
		Packing....
		Writing 256x256: /path/to/demon_front/core/target/gdx/assets/textures.png

		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

	If you're using IntelliJ IDEA, you can instead open the Maven Projects
	tab, navigate down to Demon Front Parent > Lifecycle, then
	double-click on `compile`.

	You should do this every time you change anything inside the
	core/src/main/textures directory.


Assets
-------------------------------------------------------------------------------

There are two places assets can go:

*	__core/src/main/assets__: Any files placed in here will be available
	through Gdx.files.internal or AssetManager. Generally, anything that's
	not an image should go in here.

*	__core/src/main/textures__: Any images placed in here will automatically
	be packed into a TextureAtlas file named textures.pack, which in turn
	will be available through Gdx.files.internal or AssetManager. See the
	[TexturePacker](https://github.com/libgdx/libgdx/wiki/Texture-packer)
	page on the LibGDX wiki for more information.


Building
-------------------------------------------------------------------------------

This project has two build targets: desktop and html. These targets are tied
to Maven profiles of the same name.

There is also an additional Maven profile named dev. The dev profile puts
the GWT compiler into draft compile mode and enables the PRETTY output style.

To produce a typical development build, execute:

	demon_front$ mvn -P desktop,html,dev clean package

Now you can pick up the build artifacts:

	* __desktop/target/demon_front-desktop-[version].zip__
	* __html/target/demon_front-html-[version].war__


Testing the HTML target
-------------------------------------------------------------------------------

Generally, you should develop using the desktop target, and just check the
html target every once in a while. Developing using the GWT SuperDev mode
is another possibility.

To start up a web server to test the html target, execute:

	demon_front$ mvn -P html,dev clean package tomcat7:run

	[INFO] --- tomcat7-maven-plugin:2.1:run (default-cli) @ demon_front-html ---
	[INFO] Running war on http://localhost:8080/demon_front
	[INFO] Creating Tomcat server configuration at /path/to/demon_front/html/target/tomcat
	[INFO] create webapp with contextPath: /demon_front
	Aug 10, 2013 6:10:23 PM org.apache.coyote.AbstractProtocol init
	INFO: Initializing ProtocolHandler ["http-bio-8080"]
	Aug 10, 2013 6:10:23 PM org.apache.catalina.core.StandardService startInternal
	INFO: Starting service Tomcat
	Aug 10, 2013 6:10:23 PM org.apache.catalina.core.StandardEngine startInternal
	INFO: Starting Servlet Engine: Apache Tomcat/7.0.37
	Aug 10, 2013 6:10:25 PM org.apache.coyote.AbstractProtocol start
	INFO: Starting ProtocolHandler ["http-bio-8080"]

Now you can load http://localhost:8080/demon_front in your web browser.

Press ^C to stop the web server.


Distribution
-------------------------------------------------------------------------------

1.	If you are making a branch for this version in your source control system,
	now is a good time to do so.

2.	Change the version number in all four POMs. While this is technically
	optional, the version number will appear in your artifacts, so you are
	advised to set an appropriate user-facing version number. Note that it is
	a best practice to avoid reusing non-SNAPSHOT version numbers.

3.	Update the ProGuard map by running the `package` phase of the core build:

		demon_front$ mvn clean package

		[INFO] --- proguard-maven-plugin:2.0.6:proguard (default) @ demon_front-core ---
		[INFO] execute ProGuard
		 [proguard] ProGuard, version 4.8
		 [proguard] Reading program jar [demon_front-core.jar] (filtered)
		 [proguard] Reading library jar [gdx.jar]
		 [proguard] Reading library jar [rt.jar]
		 [proguard] Preparing output jar [demon_front-core-min.jar]
		 [proguard]   Copying resources from program jar [demon_front-core.jar] (filtered)
		[INFO] ------------------------------------------------------------------------
		[INFO] Reactor Summary:
		[INFO]
		[INFO] Demon Front Parent ................ SUCCESS [0.386s]
		[INFO] Demon Front Core .................. SUCCESS [3.345s]
		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

	You should now commit core/src/main/build/proguard_map.txt to source control.

4.	Run the `package` phase for all the targets you require:

		demon_front$ mvn -P desktop,html package

		[INFO] ------------------------------------------------------------------------
		[INFO] Reactor Summary:
		[INFO]
		[INFO] Demon Front Parent ............. SUCCESS [0.338s]
		[INFO] Demon Front Core ............... SUCCESS [3.338s]
		[INFO] Demon Front Desktop ............ SUCCESS [0.564s]
		[INFO] Demon Front HTML ............... SUCCESS [1:24.175s]
		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------

    Using the `dev` profile is not recommended for release builds.

5.	Collect the distribution artifacts from each target directory.

	* __desktop/target/demon_front-desktop-[version].zip__
	* __html/target/demon_front-html-[version].war__

6.	To deploy the html target, unzip the war file into any directory that is served
	out by a web server. You can also deploy the war file to any Servlet 3 container.

