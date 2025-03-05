using GeoCoordinatePortable;
using RecomendadorDeDonacion.Domain;
using RecomendadorDeDonacion.Repositories;

namespace RecomendadorDeDonacion.Services
{
    public class LugaresService
    {
        public readonly LugaresRepository LugaresRepository;

        public LugaresService(LugaresRepository lugarRepository)
        {
            LugaresRepository = lugarRepository;
        }

        public List<Lugar> ObtenerPuntosCercanosEnRadio(Coordenada coordenada, double radio)
        {
            var lugares = LugaresRepository.GetAllLugares();
            var lugarGeoCoordinate = new GeoCoordinate(coordenada.latitud, coordenada.longitud);

            var lugaresMasCercanos = lugares.Where(l => 
            { 
                var geoCoordinate = new GeoCoordinate(l.latitud, l.longitud);

                return lugarGeoCoordinate.GetDistanceTo(geoCoordinate) < radio;

            }).ToList();

            return lugaresMasCercanos;
        }

        public double DistancianEntreDosPuntos(Lugar lugar, Lugar otroLugar) {
            
            var lugarGeoCoordinate = new GeoCoordinate(lugar.latitud, lugar.longitud);
            var otroLugarGeoCoordinate = new GeoCoordinate(otroLugar.latitud, otroLugar.longitud);

            return lugarGeoCoordinate.GetDistanceTo(otroLugarGeoCoordinate);
        }

    }
}
