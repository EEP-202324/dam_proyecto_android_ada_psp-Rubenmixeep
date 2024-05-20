package ORM.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


	
	@Entity
	@Table
	public class tasks {
		@Id
		private int id;
		private String description;
		@ManyToOne
		@JoinColumn(name="id_usuario")
		private users id_usuario;
		private int state;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public users getId_usuario() {
			return id_usuario;
		}
		public void setId_usuario(users id_usuario) {
			this.id_usuario = id_usuario;
		}
		public int isState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		
}

