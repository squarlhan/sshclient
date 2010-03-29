package
{
	[Bindable] 
	public class PafentryVO
	{
		public var id:int;
		public var unitpay:Number;
		public var staffpay:Number;
		public var total:Number;
		public var savedate:String;
		public var staff:StaffVO;
		public var ememo:String;
		
		public function PafentryVO()
		{
			unitpay = 0;
			staffpay = 0;
			total = 0;
		}

	}
}