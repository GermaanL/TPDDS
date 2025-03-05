using Microsoft.EntityFrameworkCore;
using RecomendadorDeDonacion.Domain;

namespace RecomendadorDeDonacion
{
    //para conectar sql server Server=DESKTOP-UFDF7IA;Database=RecomendadorDeDonacion;Trusted_Connection=True;TrustServerCertificate=True
    public class DBConection : DbContext
    {
        public DBConection(DbContextOptions<DBConection> options) : base(options) { }

        public DbSet<Lugar> lugares { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Lugar>(entity =>
            {
                // Configurar clave primaria autoincremental
                entity.HasKey(l => l.id);
            });

        }

    }
}
