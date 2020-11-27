package smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow;

import java.io.Serializable;
import java.util.ArrayList;

public class Accreditation  implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<String> readable, writable, executable;
	
	public Accreditation() {
		readable = new ArrayList<String>();
		writable = new ArrayList<String>();
		executable = new ArrayList<String>();
	}
	
	public ArrayList<String> getReadable() {
		return readable;
	}

	public ArrayList<String> getWritable() {
		return writable;
	}

	public ArrayList<String> getExecutable() {
		return executable;
	}

	public void addReadable(String symb){
		this.readable.add(symb);
	}
	
	public void addReadable(String...symbs){
		if(symbs == null)
			return;
		for(String symb : symbs)
			this.readable.add(symb);
	}
	
	public void addWritable(String symb){
		this.writable.add(symb);
	}
	
	public void addWritable(String...symbs){
		if(symbs == null)
			return;
		for(String symb : symbs)
			this.writable.add(symb);
	}
	
	public void addExecutable(String symb){
		this.executable.add(symb);
	}
	
	public void addExecutable(String...symbs){
		if(symbs == null)
			return;
		for(String symb : symbs)
			this.executable.add(symb);
	}
	
	public boolean isReadable(String symb){
		return this.readable.contains(symb);
	}
	
	public boolean isWritable(String symb){
		return this.writable.contains(symb);
	}
	
	public boolean isExecutable(String symb){
		return this.executable.contains(symb);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((executable == null) ? 0 : executable.hashCode());
		result = prime * result
				+ ((readable == null) ? 0 : readable.hashCode());
		result = prime * result
				+ ((writable == null) ? 0 : writable.hashCode());
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
		Accreditation other = (Accreditation) obj;
		if((executable == null && other.executable != null) || (executable != null && other.executable == null) || 
                (executable != null && other.executable != null && executable.size() != other.executable.size()))
            return false;
        for(String symb : executable)
            if(!other.executable.contains(symb))
                return false;
        if((readable == null && other.readable != null) || (readable != null && other.readable == null) || 
                (readable != null && other.readable != null && readable.size() != other.readable.size()))
            return false;
        for(String symb : readable)
            if(!other.readable.contains(symb))
                return false;
        if((writable == null && other.writable != null) || (writable != null && other.writable == null) || 
                (writable != null && other.writable != null && writable.size() != other.writable.size()))
            return false;
        for(String symb : writable)
            if(!other.writable.contains(symb))
                return false;
		return true;
	}
}