namespace RecomendadorDeDonacion.DTOs
{
    public record class LugarConDistanciaDTO
    {
        public double distancia { get; set; }
        public double latitud { get; set; }
        public double longitud { get; set; }
        public string urlGoogleMaps { get; set; }

    }
}
