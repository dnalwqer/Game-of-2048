
public class Status {
	public static final int CONTINUE = 1;
	public static final int WIN = 2;
	public static final int LOSS = 3;
	public static final int INVALID = 4;
	
	public int status;
	public Status(int status) {
		this.status = status;
	}
}
