-- $Id: logbuffer.adb,v 1.2 2003/06/12 10:53:47 spark Exp $
package LogBuffer
  --# own TheBuffer, ReadIndex, WriteIndex, HasEntries, DataOverwrittenSinceLastRead;
  --# initializes TheBuffer, ReadIndex, WriteIndex, HasEntries, DataOverwrittenSinceLastRead;

is
   MaxEntries : constant := 1024;
   subtype Index is Integer range 0..1024;
   type Buffer is array (Index) of Integer;
   EmptyBuffer : constant Buffer := Buffer'(others => 0);
   
   TheBuffer  : Buffer  := EmptyBuffer;
   ReadIndex  : Index   := Index'First;
   WriteIndex : Index   := Index'First;
   HasEntries : Boolean := False;
   DataOverwrittenSinceLastRead : Boolean := False;
   
      procedure ProtectedWrite (Data : in Integer);
      --# global in out TheBuffer;
      --#        in out WriteIndex;
      --#        in out ReadIndex;
      --#        in out DataOverwrittenSinceLastRead;
      --#        in out HasEntries;
      --# derives ReadIndex,
      --#         DataOverwrittenSinceLastRead from *,
      --#                                           ReadIndex,
      --#                                           WriteIndex,
      --#                                           HasEntries &
      --#         WriteIndex                  from * &
      --#         HasEntries                  from  &
      --#         TheBuffer                   from *,
      --#                                          WriteIndex,
      --#                                          Data;

     procedure ProtectedRead (Data       : out Integer;
                              DataMissed : out Boolean); 
      --# global in     TheBuffer;
      --#        in     WriteIndex;
      --#        in out ReadIndex;
      --#        in out DataOverwrittenSinceLastRead;
      --#           out HasEntries;
      --# derives ReadIndex                    from * &
      --#         DataOverwrittenSinceLastRead from  &
      --#         HasEntries                   from ReadIndex,
      --#                                           WriteIndex &
      --#         Data                         from ReadIndex,
      --#                                           TheBuffer &
      --#         DataMissed                   from DataOverwrittenSinceLastRead;

     procedure Write (Data : in Integer);
      --# global in out TheBuffer;
      --#        in out WriteIndex;
      --#        in out ReadIndex;
      --#        in out DataOverwrittenSinceLastRead;
      --#        in out HasEntries;
      --# derives ReadIndex,
      --#         DataOverwrittenSinceLastRead from *,
      --#                                           ReadIndex,
      --#                                           WriteIndex,
      --#                                           HasEntries &
      --#         WriteIndex                  from * &
      --#         HasEntries                  from  &
      --#         TheBuffer                   from *,
      --#                                          WriteIndex,
      --#                                          Data;

     
     procedure Read (Data       : out Integer;
                     DataMissed : out Boolean);
      --# global in     TheBuffer;
      --#        in     WriteIndex;
      --#        in out ReadIndex;
      --#        in out DataOverwrittenSinceLastRead;
      --#           out HasEntries;
      --# derives ReadIndex                    from * &
      --#         DataOverwrittenSinceLastRead from  &
      --#         HasEntries                   from ReadIndex,
      --#                                           WriteIndex &
      --#         Data                         from ReadIndex,
      --#                                           TheBuffer &
      --#         DataMissed                   from DataOverwrittenSinceLastRead;

   
end LogBuffer;


package body LogBuffer
is
   
      procedure ProtectedWrite (Data : in Integer)
      is
      begin
         TheBuffer (WriteIndex) := Data;

         if HasEntries and
           WriteIndex = ReadIndex
         then
            -- We have just overwritten the oldest entry
            -- Move the read pointer to the next oldest entry and mark that
            -- data has been overwitten.
            ReadIndex := ReadIndex + 1;
            DataOverwrittenSinceLastRead := True;
	 else
	    null;
         end if;
         WriteIndex := WriteIndex + 1;
         HasEntries := True;
      end ProtectedWrite;
      
      
      procedure ProtectedRead (Data       : out Integer;
                               DataMissed : out Boolean)
      is
      begin
         Data       := TheBuffer (ReadIndex);
         ReadIndex  := ReadIndex + 1;
         HasEntries := ReadIndex /= WriteIndex;
         DataMissed := DataOverwrittenSinceLastRead;
         DataOverwrittenSinceLastRead := False;
      end ProtectedRead;

   procedure Write (Data : in Integer)
   is
   begin
      ProtectedWrite (Data);
   end Write;

   procedure Read (Data       : out Integer;
                   DataMissed : out Boolean)
   is
   begin
      ProtectedRead (Data, DataMissed);
   end Read;      

end LogBuffer;
