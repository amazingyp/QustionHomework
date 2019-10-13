package bean;

/**
 * 数据类型定义
 *
 */
public class Num {
	private int son;//分子
	private int mother;//分母
	
	
	public Num(int son,int mother) {
		this.son = son;
		this.mother = mother;
	}
	public int getSon() {
		return son;
	}
	
	public int getMother() {
		return mother;
	}
	
	public void setSon(int son) {
		this.son = son;
	}
	
	public void setMother(int mother) {
		this.mother = mother;
	}
	
	public String toString(){
        return son+"/"+mother;
    }
}
