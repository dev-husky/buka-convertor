package asia.laevatein.buka.model;

import java.util.List;

public class ChapOrder {
	private String name;
	private String logo;
	private String author;
	private String lastuptimeex;
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLastuptimeex() {
		return lastuptimeex;
	}
	public void setLastuptimeex(String lastuptimeex) {
		this.lastuptimeex = lastuptimeex;
	}

	public static class Chap implements Comparable<Chap> {
		public static final int TYPE_EPISODE = 0;
		public static final int TYPE_CHAPTER = 1;
		public static final int TYPE_LEGEND = 2;
		
		private String cid;
		private int idx;
		private int type;
		private String title;
		
		private int pageIndex;
		
		public String getCid() {
			return cid;
		}
		public void setCid(String cid) {
			this.cid = cid;
		}
		
		public int getIdx() {
			return idx;
		}
		public void setIdx(int idx) {
			this.idx = idx;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getPageIndex() {
			return pageIndex;
		}
		public void setPageIndex(int pageIndex) {
			this.pageIndex = pageIndex;
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
		public int compareTo(Chap o) {
			if (this.type < o.getType()) {
				// 2(番外), 1(单行本), 0(连载), 连载优先
				return -1;
			} else if (this.type > o.getType()) {
				return 1;
			} else {
				// type 相同参考 idx, idx大的优先
				if (this.idx > o.getIdx()) {
					return -1;
				} else if (this.idx < o.getIdx()) {
					return 1;
				}
			}
			return 0;
		}
	}
}
