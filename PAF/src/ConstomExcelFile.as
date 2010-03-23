package
{
	import com.as3xls.biff.BIFFVersion;   
    import com.as3xls.biff.BIFFWriter;   
    import com.as3xls.biff.Record;   
    import com.as3xls.xls.ExcelFile;   
    import com.as3xls.xls.Sheet;   
    import com.as3xls.xls.Type;   
       
    import flash.utils.ByteArray;   
       
    import mx.utils.StringUtil;   
       
       
    //注释   
       
    public class ConstomExcelFile extends ExcelFile   
    {   
        public function ConstomExcelFile()   
        {   
               
        }   
          private function GetStringLength(objString:String):int  
     {   
         var temp:int = 0;   
         for(var i:int=0;i<objString.length;i++)   
         {   
             if(isChinese(objString.substring(i,i+1)))   
             {   
                 temp += 2;   
             }   
             else{temp += 1;}   
         }    
                
         return temp;   
     }   
     //判断是否是中文   bryan add   
         private function isChinese(char:String):Boolean{   
             if(char == null){   
                 return false;   
             }   
               
             char =  StringUtil.trim(char);   
             var pattern:RegExp = /^[\u0391-\uFFE5]+$/;    
             var result:Object = pattern.exec(char);   
             if(result == null) {   
                 return false;   
             }   
             return true;   
         }   
        override public function saveToByteArray():ByteArray{   
        //  var s:Sheet = _sheets[0] as Sheet; //这行源代码 改成如下   
            var s:Sheet =super.sheets[0] as Sheet;   
               
            var br:BIFFWriter = new BIFFWriter();   
               
            // Write the BOF and header records   
            var bof:Record = new Record(Type.BOF);   
            bof.data.writeShort(BIFFVersion.BIFF2);   
            bof.data.writeByte(0);   
            bof.data.writeByte(0x10);   
            br.writeTag(bof);   
               
            // Date mode   
            var dateMode:Record = new Record(Type.DATEMODE);   
            dateMode.data.writeShort(1);   
            br.writeTag(dateMode);   
               
            // Store built in formats   
            var formats:Array = ["General",    
                "0", "0.00", ",0", ",0.00",    
                "", "", "", "",   
                "0%", "0.00%", "0.00E+00",   
                "?/?", "??/??",   
                "M/D/YY", "D-MMM-YY", "D-MMM", "MMM-YY"];   
               
            var numfmt:Record = new Record(Type.BUILTINFMTCOUNT);   
            numfmt.data.writeShort(formats.length);   
            br.writeTag(numfmt);   
               
            for(var n:uint = 0; n < formats.length; n++) {   
                var fmt:Record = new Record(Type.FORMAT);   
                fmt.data.writeByte(formats[n].length);   
                fmt.data.writeUTFBytes(formats[n]);   
                br.writeTag(fmt);   
            }   
               
            var dimensions:Record = new Record(Type.DIMENSIONS);   
            dimensions.data.writeShort(0);   
            dimensions.data.writeShort(s.rows+1);   
            dimensions.data.writeShort(0);   
            dimensions.data.writeShort(s.cols+1);   
            br.writeTag(dimensions);   
               
            for(var r:uint = 0; r < s.rows; r++) {   
                for(var c:uint = 0; c < s.cols; c++) {   
                    var value:* = s.getCell(r, c).value;   
                    var cell:Record = new Record(1);   
                    cell.data.writeShort(r);   
                    cell.data.writeShort(c);   
                       
                    if(value is Date) {   
                        var dateNum:Number = (value.time / 86400000) + 24106.667;   
                        cell.type = Type.NUMBER;   
                        cell.data.writeByte(0);   
                        cell.data.writeByte(15);   
                        cell.data.writeByte(0);   
                        cell.data.writeDouble(dateNum);   
                    } else if(isNaN(Number(value)) == false && String(value) != "") {   
                        cell.type = Type.NUMBER;   
                        cell.data.writeByte(0);   
                        cell.data.writeByte(0);   
                        cell.data.writeByte(0);   
                        cell.data.writeDouble(value);   
                    } else if(String(value).length > 0) {   
                        cell.type = Type.LABEL;   
                        cell.data.writeByte(0);   
                        cell.data.writeByte(0);   
                        cell.data.writeByte(0);   
                    //  var len:uint = String(value).length; //源文 改成如下   
                        var len:uint=this.GetStringLength(value);   
                        cell.data.writeByte(len);   
                    //  cell.data.writeUTFBytes(value);//这行源代码 改成如下   
                        cell.data.writeMultiByte(value,"gbk");   
                    } else {   
                        cell.type = Type.BLANK;   
                        cell.data.writeByte(0);   
                        cell.data.writeByte(0);   
                        cell.data.writeByte(0);   
                    }   
                       
                    br.writeTag(cell);   
                }   
            }   
               
               
            // Finally, the closing EOF record   
            var eof:Record = new Record(Type.EOF);   
            br.writeTag(eof);   
               
            br.stream.position = 0;   
            return br.stream;   
        }   
  
    }   

}