sireum-core
###########

This repository stores some of the core components of the Sireum (v2) software analysis platform.

Setting up Development Environment
**********************************

1. Download and install Sireum by following the instructions at: http://www.sireum.org/download.

2. Download the `DML in Scala (DMS) project set file <https://github.com/mdcf/devicemodel/blob/master/dms.psf>`_.
   If you are a MDCF team member at GitHub with SSH key installed, you can use the 
   `project set file with write access <https://github.com/mdcf/devicemodel/blob/master/dms.psf>`_ instead.

3. Launch `Sireum Development Environment (DE) <http://www.sireum.org/features>`_
   (add ``-h`` for help)::

       sireum launch sireumdev

   Pick a directory for your workspace when asked. Moreover, agree when asked to run Scala diagnostics;
   enable JDT Weaving for Scala IDE and then quit DE; relaunch Sireum DE.

4. Add ``SIREUM_HOME/apps/platform/java`` in Eclipe's 
   `Java Installed JREs preference page <http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Fdebug%2Fref-installed_jres.htm>`_,
   and make it the default. In addition, set Eclipse's `Java Compiler compliance level <http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Fref-preferences-compiler.htm>`_ to 1.7.
   (If Eclipse shows you a dialog box indicating that "Subversive Native Library Not Available",
   it means that you do not have native SVN installed; click Ok to close the dialog box.
   You can correct this issue by setting SVN Interface Client to "SVNKit" in the 
   Eclipse's Team->SVN preference page.)

5. Import all projects in:

   * `Sireum Core Prelude Repo <https://www.assembla.com/code/sireum-core/git-3/nodes>`_
   
     * read-only: ``git://git.assembla.com/sireum-core.prelude.git``
     
     * read-write: ``git@git.assembla.com:sireum-core.prelude.git``
     
   * `Sireum Core Repo <https://www.assembla.com/code/sireum-core/git/nodes>`_
    
     * read-only: ``git://git.assembla.com/sireum-core.git``
     
     * read-write: ``git@git.assembla.com:sireum-core.git``
     
   This should import projects such as ``sireum-core-test``.

6. Run all Sireum Core tests by right clicking the project ``sireum-core-test``
   and selecting ``Run As`` and then ``Scala JUnit Test``; all tests should pass.
   

7. If you are making changes to the codebase, import the following in the 
   respective Eclipse preference pages:
 
   * In `Java -> Code Style -> Clean Up preference page <http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Fcodestyle%2Fref-preferences-cleanup.htm>`_:
     `sireum-util/codestyle/java-clean-up.xml <https://www.assembla.com/code/sireum-core/git-3/nodes/master/sireum-util/codestyle/java-clean-up.xml>`_
   
   * In `Java -> Code Style -> Formatter preference page <http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Fcodestyle%2Fref-preferences-formatter.htm>`_: 
     `sireum-util/codestyle/java-formatter.xml <https://www.assembla.com/code/sireum-core/git-3/nodes/master/sireum-util/codestyle/java-formatter.xml>`_
   
   * In `Scala -> Formatter preference page <http://scala-ide.org/docs/current-user-doc/features/typingviewing/formatting/index.html>`_: 
     `sireum-util/codestyle/scala-formatterPreferences.properties <https://www.assembla.com/code/sireum-core/git-3/nodes/master/sireum-util/codestyle/scala-formatterPreferences.properties>`_
   
   Make sure to run Java source clean up before committing Java code.
   Similarly, make sure to run Scala formatter before committing Scala code. 
    