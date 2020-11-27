package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;

public class Task implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String 
			TASK_TYPE_CLASSIC = "Classic",
			TASK_TYPE_VIRTUAL = "Virtual";
	
	protected String symbol;
	protected String desc;
	protected String status;
	protected String type;
	
	public Task(String symbol, String type, String desc) {
		this.symbol = symbol;
		this.type = type;
		this.desc = desc;
		this.status = null;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result
				+ ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		/*if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;*/
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	public boolean isVirtual() {
		return type.equals(TASK_TYPE_VIRTUAL);
	}
	
	public boolean isClassic(){
		return !isVirtual();
	}

	@Override
	public String toString() {
		return "Task [symbol=" + symbol + ", desc=" + desc + ", type=" + type
				+ "]";
	}
	
	
}