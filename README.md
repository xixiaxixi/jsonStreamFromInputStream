# jsonStreamFromInputStream
jsonStreamFromInputStream provides a solution of <br>
getting jsonObject or jsonArray via tcp or other inputstream steadily. <br>

JsonReader class<br>
provides a simple implement which can constantly get jsonObject or jsonArray from InputStream.<br>

Main.java is a example

可以从InputStream中连续获得json对象。当然，它是同步的，不安全的（因为我菜）<br>

2018.06.13<br>
now i hava a thread<br>
but can't be stopped<br>
