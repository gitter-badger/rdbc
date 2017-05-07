[![Build Status](https://travis-ci.org/badges/shields.svg?branch=master)](https://travis-ci.org/badges/shields)
[![codecov](https://codecov.io/gh/rdbc-io/rdbc/branch/master/graph/badge.svg)](https://codecov.io/gh/rdbc-io/rdbc)

## What is rdbc?

rdbc is a SQL-level relational database connectivity API targeting Scala and 
Java programming languages. The API is fully asynchronous and provides
a possibility to leverage [Reactive Streams'](http://www.reactive-streams.org/)
stream processing capabilities.

See the documentation at [http://rdbc.io](http://rdbc.io).

## Goals

Following list outlines the goals of the API:

1. **Provide vendor neutral access to most commonly used database features.**

    The API is meant to be vendor neutral in a sense that if clients stick
    to using only standard SQL features no vendor-specific code should be needed
    and database backends can be switched with no client code changes.

2. **Be asynchronous and reactive.**

    All methods that can potentially perform I/O actions don't block the executing
    thread so the API fits well into non-blocking application design. rdbc
    allows building applications according to the [Reactive Manifesto](http://www.reactivemanifesto.org/)
    by using [Reactive Streams](http://www.reactive-streams.org/) for asynchronous
    streaming results with a back-pressure.
   
3. **Provide a foundation for higher-level APIs.**

    rdbc is a rather low-level API enabling clients to use plain SQL queries
    and get results back. While it can be used directly it's also meant to 
    provide a foundation for higher-level APIs like functional or object
    relational mapping libraries.