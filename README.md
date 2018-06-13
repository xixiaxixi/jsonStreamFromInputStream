# jsonStreamFromInputStream
jsonStreamFromInputStream provides a solution of 
getting jsonObject or jsonArray via tcp or other inputstream steadily.

JsonReader class
provides a simple implement which can constantly get jsonObject or jsonArray from InputStream.

Main.java is a example

可以从InputStream中连续获得json对象。当然，它是同步的，不安全的（因为我菜）

2018.06.13
now i hava a thread
but can't be stopped
