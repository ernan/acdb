# acdb
An android implementation of CDB database

This is an implementation of [CDB] (https://cr.yp.to/cdb.html)

CDB is useful if you have a static or relatively static database.

The key concepts of CDB

1. Fast lookups
2. Low overhead
3. No size limits
4. Fast replacement

I used the source code from [strangeGizmo.com](http://www.strangegizmo.com/products/sg-cdb/)

For me the testing went very well.
I am seeing five to ten times improvement in speed over sqlite.

