package yunfucloud.com.myapp.bean;


public class ProductInfo extends BaseInfo
{
	private String desc;
	private double price;
	private int count;
	private int position;// 绝对位置，只在ListView构造的购物车中，在删除时有效
	private String repayInterest;   //应还利息
	private String repayFee;   //应还服务费
	private String lateFee;   //滞纳金
	private String time;   //还款时间
	private int status;    //状态（0未还款 1.已还款 2.逾期）

	public ProductInfo()
	{
		super();
	}

	public ProductInfo(String id, String name, String desc, double price, int count, String repayInterest, String repayFee, String lateFee, String time, int status)
	{
		super.Id = id;
		super.name = name;
		this.desc = desc;
		this.price = price;
		this.count = count;
		this.repayInterest = repayInterest;
		this.repayFee = repayFee;
		this.lateFee = lateFee;
		this.time = time;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}

	public String getRepayFee() {
		return repayFee;
	}

	public void setRepayFee(String repayFee) {
		this.repayFee = repayFee;
	}

	public String getLateFee() {
		return lateFee;
	}

	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

}
