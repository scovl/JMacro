## JMacro

This is a program that allows you to create macros based on keyboard and mouse. With it, you can remap keys and combine keyboard and mouse actions to be performed with just one or more commands. **[Read the Documentation here](https://github.com/lobocode/JMacro/blob/main/docs/macro.md)**.

## Features

* Combine keyboard actions

## Requirements

* Java 8 or higher (recommend java 11)

## How to use

1. Download the release **[HERE](https://github.com/lobocode/JMacro/releases/download/Latest/JMacro.zip)** and unzip `JMacro.zip` anywhere
2. Configure the `configMacro.properties` file with the macro you want to configure
3. run it with the following command:

```bash
java -jar JMacro.jar
```

**[Read the Documentation here](https://github.com/lobocode/JMacro/blob/main/docs/macro.md)**

* **NOTE**: Pay attention to variables `macroStartKey` and `macroEndKey` (one is for starting the macro and the other for ending it).

Easy, isn't it? JMacro will be guided by the configuration you made in the `configMacro.properties` file.

## TODO

* Implement keyboard remapping feature
* Implement feature that allows you to create more complex macros that mix mouse and keyboard
