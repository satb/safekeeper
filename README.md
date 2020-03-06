Safekeeper is a simple java program that does symmetric encryption of some user provided text with some
user provided secret. 

Upon building it, a safekeeper binary is generated that can be run directly without the need for installing 
the JVM on any machine. It uses GraalVM native image building capabilities to generate the binary

* Building & Running
  * Install the graalvm version found here - https://github.com/oracle/graal/releases/tag/vm-19.2.1
  * We are using the specific version of graalvm because the maven plugin available today is 19.2.1 which 
  should match the version of graalvm it is being built against
  * Set JAVA_HOME environment variable to the installed and unpacked graalvm. For example, if you 
    downloaded the Mac version of graavm, then run this on the terminal before building
    ```> export JAVA_HOME = <graalvm_install_dir>/Contents/Home```
  * Then run the build command ```> mvn clean package```
  * After the build is complete, you will find the safekeeper binary in the target folder, run it like this
    ```> ./target/safekeeper``` and follow the instructions that follow
  * Remember, when the "secret" is asked for, enter **16 characters**
    
    
      