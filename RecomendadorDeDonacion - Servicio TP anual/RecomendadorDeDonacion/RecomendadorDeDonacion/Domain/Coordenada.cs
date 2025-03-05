namespace RecomendadorDeDonacion.Domain
{
    public class Coordenada
    {
        public double latitud { get; set; }
        public double longitud { get; set; }

        public Coordenada(double latitud, double longitud) { 
            this.latitud = latitud;
            this.longitud = longitud; 
        
        }
    }
}
