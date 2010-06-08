package
{
	import org.libspark.thread.Thread;
	import flash.display.Sprite;
	import org.libspark.thread.EnterFrameThreadExecutor;
	import mx.collections.ArrayCollection;
	import flash.data.SQLConnection;
	import flash.data.SQLStatement;
	import flash.data.SQLResult;
	import flash.filesystem.*;
	import flash.filesystem.File;
	import flash.filesystem.FileStream;
	import mx.controls.Alert;
	import mx.controls.DataGrid;
	import mx.controls.DateField;
	import mx.events.*;	
	import mx.managers.PopUpManager;
	
	public class Dosth extends Thread
	{
		public var file:File = File.applicationDirectory.resolvePath("paf.db");
		public var con:SQLConnection;
		public var tw;
		
		public function Dosth(tw)
		{
			if (! Thread.isReady)Thread.initialize (new EnterFrameThreadExecutor ());
			super();
			this.tw = tw;
		}
		
		override protected function run (): void
		{
			
			addall();
		}
		
		public function query(sqltxt:String): ArrayCollection
		{    
			con = new SQLConnection();
			var result:ArrayCollection = new ArrayCollection()
			if(file.exists)
			{
				try{
					con.open(file);
					trace("连接成功！");
					var stmt:SQLStatement = new SQLStatement();
					stmt.sqlConnection = con;
					stmt.text = sqltxt;
					stmt.execute();
					var dbresult:SQLResult = stmt.getResult();                       
					var leng:int = dbresult.data.length;
					if(leng>=1)
					{
						for(var i:int = 0;i<=leng-1;i++)
						{
							result.addItem(dbresult.data[i]);
						}
					}                       
					con.close();
				}catch(e:Error){
					trace(e.message);
				}
			}  
			return result; 
		}
		
		public function addall():void{
			var dates:ArrayCollection = new ArrayCollection();
			dates = this.query("select distinct lastdate from staff_table order by lastdate desc");
			var today:String = DateField.dateToString(new Date(),"YYYY/MM/DD");
			if(String(dates[0].lastdate).length==10)
			{
				var pre:String = String(dates[0].lastdate).substr(0,7);
				if(today.substr(0,7)==pre)
				{
					Alert.show("本月记录已添加！");
					return;
				}
			}
			var staffs:ArrayCollection = new ArrayCollection();
			staffs = this.query("select staffno,staffrate,unitrate,paybase,balance,state from staff_table");
			for(var i:int;i<=staffs.length-1;i++)
			{
				if(staffs[i].state=="正常")
				{
					var newstaffrate:Number = staffs[i].staffrate;
					var newunitrate:Number = staffs[i].unitrate;
					var newpaybase:Number = staffs[i].paybase;
					var newstaffno:String = staffs[i].staffno;
					var newstaffpay:Number = Number((newstaffrate*newpaybase).toFixed(2));
					var newunitpay:Number = Number((newunitrate*newpaybase).toFixed(2));
					var newtotal:Number = newstaffpay+newunitpay;
					var newbalance:Number = staffs[i].balance+newtotal;
					var addsql:String = "insert into pafentry_table(unitpay,staffpay,total,savedate,staffno,ememo) values(";
					addsql=addsql+newunitpay+","
						+newstaffpay+","
						+newtotal+",'"
						+today+"','"
						+newstaffno+"','"
						+"月基本公积金"+"');";
					trace(addsql);	

					this.query(addsql);
					this.query("update staff_table set balance="+newbalance+", lastdate='"+today+"' where staffno='"+newstaffno+"';");			        								

				}				
			}
		}
	}
}