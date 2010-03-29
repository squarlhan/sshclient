package
{
	[Bindable] 
	public class StaffVO
	{
		public var account:String;
		public var departid:String;
		public var depart:String;
		public var staffno:String;
		public var staffname:String;
		public var staffid:String;
		public var paybase:Number;
		public var staffrate:Number;
		public var unitrate:Number;		
		public var balance:Number;
		public var opendate:String;				
		public var lastdate:String;		
		public var state:String;
		public var memo:String;
		
		public function StaffVO()
		{
			paybase = 0;
			staffrate = 0;
			unitrate = 0;
			balance = 0;
		}

	}
}