package
{
	import flash.events.Event;
	import mx.collections.ArrayCollection;
       
       public class PageChangeEvent extends flash.events.Event
       {
              public function PageChangeEvent(pageindex:int,pagesize:int)
              {
                     super("PageChange");
                     PageIndex= pageindex;
                     PageSize =pagesize;
              }
              public var PageIndex:int=0;
              public var PageSize:int=0;
              public function Filter(list:ArrayCollection):ArrayCollection
              {
                     var newlist:ArrayCollection = new ArrayCollection();
                     var start:int = PageSize*PageIndex;
                     var end:int = start+PageSize;
                     for(var i:int = start;i<end;i++)
                     {
                            if(i< list.length)
                            {
                                   newlist.addItem(list[i]);
                            }
                            else
                                   break;
                     }
                     return newlist;
              }
       }
}