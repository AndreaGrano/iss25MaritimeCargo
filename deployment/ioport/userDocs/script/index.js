// Generazione automatica dell'indice
const tocList = document.getElementById('toc-list');
const headers = document.querySelectorAll('h2, h3');
headers.forEach(header => {
  const id = header.textContent.trim().toLowerCase().replace(/\s+/g, '-').replace(/[^\w-]/g, '');
  header.id = id;
  const li = document.createElement('li');
  li.style.marginLeft = header.tagName === 'H3' ? '1em' : '0';
  li.innerHTML = `<a href="#${id}">${header.textContent}</a>`;
  tocList.appendChild(li);
});