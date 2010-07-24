# test clojure.xml

A hand-full of recent tickets describe some problems with the
clojure.xml namespace:

 - [408](http://www.assembla.com/spaces/clojure/tickets/408)
   clojure.xml emit does not properly escape attribute and element content.
 - [410](http://www.assembla.com/spaces/clojure/tickets/410)
   clojure.xml parse/emit do not round-trip
 - [411](http://www.assembla.com/spaces/clojure/tickets/411)
   clojure.xml/emit should be encoding-aware

In attempting to fix these problems, I discovered that it's not
actually possible to unit test clojure.xml from within the clojure's
own build:

 - [409](http://www.assembla.com/spaces/clojure/tickets/409)
   SAXParserFactoryImpl is missing at unit testing time

This project exists to work around #409, allowing me to test
clojure.xml from the "outside". The unit tests defined here
demonstrate #408 and #410.

## Testing clojure.xml

This project uses Maven for its build:

    $ mvn test

This will run the tests against clojure.jar 1.2-beta1, as defined in
the POM. You may override this default to use a locally built
clojure.jar:

    $ mvn test -Dclojure.jar=/absolute/path/to/clojure.jar

