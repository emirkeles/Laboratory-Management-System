package laboratuvarYonetimSistemi;

public class Laboratuvar {
	private String name;
	private String faculty;
	private String department;
	private String owner;
	private float price;

	Laboratuvar() {

	}

	Laboratuvar(String name) {
		this.name = name;
	}

	Laboratuvar(String name, String faculty) {
		this(name);
		this.faculty = faculty;
	}

	Laboratuvar(String name, String faculty, String department) {
		this(name, faculty);
		this.department = department;
	}

	Laboratuvar(String name, String faculty, String department, String owner) {
		this(name, faculty, department);
		this.owner = owner;
	}

	Laboratuvar(String name, String faculty, String department, String owner, float price) {
		this(name, faculty, department, owner);
		this.price = price;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
