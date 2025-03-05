export async function obtenerOfertas() {
    try {
        const response = await fetch('/src/js/services/data/ofertas.json'); // Ruta desde el archivo JS
        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error al cargar los datos:', error);
        return []; // Retorna un array vacío en caso de error
    }
}