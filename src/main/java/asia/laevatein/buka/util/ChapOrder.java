package asia.laevatein.buka.util;

import java.util.List;

public class ChapOrder {
	private String name;
	private List<Chap> links;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Chap> getLinks() {
		return links;
	}
	public void setLinks(List<Chap> links) {
		this.links = links;
	}

	public static class Chap {
		private String cid;
		private String idx;
		private String type;
		private String title;
		public String getCid() {
			return cid;
		}
		public void setCid(String cid) {
			this.cid = cid;
		}
		public String getIdx() {
			return idx;
		}
		public void setIdx(String idx) {
			this.idx = idx;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof Chap) {
				if (this.cid != null) {
					return this.cid.equals(((Chap) o).getCid());
				}
				return ((Chap) o).getCid() == null;
			}
			return false;
		}
		@Override
		public int hashCode(){
			return this.cid.hashCode();
		}
	}
}
