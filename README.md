A junior developer was tasked with writing a reusable implementation for a mass mailing application to read and write text files that hold tab separated data.
 
 His implementation, although it works and meets the needs of the application, is of very low quality.
 
 The task:
 -
 
 - Identify and annotate the shortcomings in the current implementation as if you were doing a code review, using comments in the source files.
 
 - Refactor the CSVReaderWriter implementation into clean, elegant, rock-solid & well performing code.

 - Provide evidence that the code is working as expected.
 
 - Where you make trade offs, comment & explain.
 
 - Assume this code is in production and backwards compatibility must be maintained. Therefore if you decide to change the public interface, please deprecate the existing methods. Feel free to evolve the code in other ways though. You have carte blanche while respecting the above constraints. Delight us.
 
 
 What I've done:
 - 
 
 - Added code review comments and tests for the original code in original-code branch.
 - Also created a pull request with those comments.
 - Created improved-code branch with the refactored code.
 
 - To run the tests:
    - JDK 8 is required to run this project
    - `$ gradle test` on the command line
