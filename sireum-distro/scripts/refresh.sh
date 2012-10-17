cat sireum-header ../src/main/scala/SireumDistro.scala > sireum
cat sireum-header.bat ../src/main/scala/SireumDistro.scala > sireum.bat
/opt/local/bin/unix2dos sireum-header.bat
/opt/local/bin/unix2dos sireum.bat
[ ! -e ../test ] && mkdir ../test
cp sireum ../test/
cp sireum.bat ../test/
cp ../src/main/scala/SireumDistro.scala ../../../../project/
