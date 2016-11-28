modastylz admin API handlers
============================

This is the modastylz admin handler for Project modastylz.

This project contains just one main verticle which is responsible for listening Brands, Styles, Products, Tryitusersave, and Tryitupdated for  address on message bus.

DONE
----
* Configured listener
* Provided a initializer and finalizer mechanism for components to initialize and clean up themselves
* Processor layer is created which is going to take over the message processing from main verticle once message is read
* Logging and app configuration
* Transformer layer working for positive cases
* DB layer to actually do the operations
* Transformer layer implementation so that output from DB layer could be transformed and written back to message bus

**API features**


To understand build related stuff, take a look at <a href="BUILD_README.md">BUILD_README</a>

