package
{
	[Bindable] 
	public class PafoutVO
	{
		public var id:int;
		public var drawtotal:Number;
		public var drawdate:String;
		public var staff:StaffVO;
		public var omemo:String;
		
		public function PafoutVO()
		{
			drawtotal = 0;
		}

	}
}