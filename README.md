
---

# 🕸️ Web Crawler Project

## Table of Contents

- [About Me](#about-me)
- [Overview](#overview)
- [JavaDoc](#-javadoc)
- [Project Structure](#-project-structure)
- [Usage](#-usage)
- [Design Patterns](#-design-patterns)

---

# About Me

- 💁 Name: Shahar Asher
- 📫 Email: [shaharas@edu.hac.ac.il](mailto:shaharas@edu.hac.ac.il)

---

## Overview

This project is a Java-based multi-threaded web crawler. It reads URLs from a specified file, downloads content, and outputs results in different formats. The application allows for configuring output formats, thread pool size, and uses design patterns to maintain flexibility and scalability.

---

## 📚 JavaDoc
To view the generated JavaDoc:

1. Open the [doc](./doc) directory.
2. Locate the [index.html](./doc/index.html) file.
3. Open it with a web browser to explore the documentation.

---
## 📂 Project Structure

The project contains several Java classes with the following roles:

- **Main.java**: The main entry point for running the program. It handles command-line arguments and initializes the web crawler.
- **Controller.java**: Manages the crawling process, including reading URLs, setting up a thread pool, and handling output formats.
- **Downloader.java**: An abstract base class for downloading content from a given URL. It defines common behavior for handling HTTP requests and output processing.
- **ImageDownloader.java**: A concrete subclass of `Downloader` specialized for downloading image content.
- **FormatFactory.java**: A factory class responsible for creating instances of output formats based on provided types.
- **OutFormat.java**: An interface that defines a contract for various output formats.
- **SizeFormat**, **UrlFormat**, **TimeFormat**, **ImagTypeFormat**: Implementations of `OutFormat` that represent different output formats for the web crawler.
- **FileReaderComp.java**: A class that reads URLs from a specified file.

---

## 🚀 Usage

To run the web crawler, follow these steps:

1. **Compile the Code**:
   Compile all Java files to ensure the project is built properly.

   ```bash
   javac ex2/*.java
   ```

2. **Run the Application**:
   Execute the program with the appropriate command-line arguments: the output format, thread pool size, and the file name containing the URLs to be crawled.

   ```bash
   java ex2.Main <output format> <pool size> <file name>
   ```

    - **Output Format**: A string indicating the desired output formats, such as 's' for size, 'u' for URL, 't' for time, and 'm' for image type.
    - **Pool Size**: An integer representing the number of threads for multi-threaded execution.
    - **File Name**: The name of the file that contains the list of URLs to be crawled.

Example command:
```bash
java ex2.Main sutm 4 urls.txt
```

---

## 💡 Design Patterns

This project uses several design patterns to enhance maintainability and flexibility:

- **Factory Pattern**: Implemented in `FormatFactory`, allowing dynamic creation of output formats. This pattern solves the problem of creating format-specific instances without changing the core logic.

- **Template Method Pattern**: Used in the `Downloader` class, which provides a common structure for downloading content, allowing subclasses like `ImageDownloader` to define specific behaviors. This pattern addresses the problem of code duplication by centralizing common behavior and providing a template for extensions.

- **Strategy Pattern**: Implemented with the `OutFormat` interface and its different implementations. It provides flexibility in choosing output formats at runtime, solving the problem of hard coding specific behaviors.

These patterns contribute to the scalability and maintainability of the code, enabling easy addition of new formats and download behaviors.

---
