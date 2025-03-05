using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using RecomendadorDeDonacion.Domain;
using RecomendadorDeDonacion.DTOs;
using RecomendadorDeDonacion.Services;

namespace RecomendadorDeDonacion.Controllers
{
    [Route("servicio/[controller]")]
    [ApiController]
    public class LugaresController : ControllerBase
    {
        private readonly DBConection lugaresDBContext;
        private readonly LugaresService lugaresService;

        public LugaresController(DBConection lugaresRepository, LugaresService lugaresServicio)
        {
            lugaresDBContext = lugaresRepository;
            lugaresService = lugaresServicio;
        }

        [HttpPost]
        public async Task<ActionResult<Lugar>> CrearLugar(Lugar lugar)
        {
            lugaresDBContext.lugares.Add(lugar);
            await lugaresDBContext.SaveChangesAsync();

            return Created();
        }

        //[HttpPost("puntoMasCercano")]
        [HttpGet]
        public ActionResult<List<LugarConDistanciaDTO>> ObtenerPuntoMasCercano(double latitud, double longitud, double radio)
        {
            Coordenada coordenada = new Coordenada(latitud, longitud);

            var puntosMasCercanos = lugaresService.ObtenerPuntosCercanosEnRadio(coordenada, radio);

            var lugaresCercanos = new List<LugarConDistanciaDTO>();

            puntosMasCercanos.ForEach(x => {

                var lugar = new Lugar(latitud, longitud);

                var lugarCercano = new LugarConDistanciaDTO
                {
                    latitud = x.latitud,
                    longitud = x.longitud,
                    distancia = lugaresService.DistancianEntreDosPuntos(lugar, x),
                    urlGoogleMaps = string.Format("https://www.google.com/maps/search/?api=1&query={0}%2C{1}", x.latitud, x.longitud),

                };

                lugaresCercanos.Add(lugarCercano);

            });

            if (!lugaresCercanos.Any())
            {
                return new ObjectResult("No se encontraron lugares cercanos") { StatusCode = 404 };
            }
            
            return new ObjectResult(lugaresCercanos) { StatusCode = 200 };

        }

    }
}
