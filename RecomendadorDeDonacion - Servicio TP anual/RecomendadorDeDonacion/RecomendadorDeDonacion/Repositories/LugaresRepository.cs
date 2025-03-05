using RecomendadorDeDonacion.Domain;

namespace RecomendadorDeDonacion.Repositories
{
    public class LugaresRepository
    {
        private readonly DBConection db;

        public LugaresRepository(DBConection db)
        {
            this.db = db;
        }

        public List<Lugar> GetAllLugares()
        {
            return db.lugares.ToList();
        }
    }
}
