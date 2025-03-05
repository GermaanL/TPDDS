namespace RecomendadorDeDonacion.Domain
{
    public class Lugar
    { 
        public int id { get; set; }
        public int altura {  get; set; }
        public string calle {  get; set; } = string.Empty;
        public int codigoPostal { get; set; }
        public string localidad { get; set; } = string.Empty ;
        public string provincia { get; set; } = string.Empty;
        public double latitud { get; set; }
        public double longitud { get; set; }

        public Lugar(int altura, string calle, int codigoPostal, string localidad, string provincia, double latitud, double longitud)
        {
            this.altura = altura;
            this.calle = calle;
            this.codigoPostal = codigoPostal;
            this.localidad = localidad;
            this.provincia = provincia;
            this.latitud = latitud;
            this.longitud = longitud;
        }

        public Lugar(double latitud, double longitud) {
            this.latitud = latitud;
            this.longitud = longitud;
        }
    }
}
