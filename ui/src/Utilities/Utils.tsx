export const extractFilenameFromContentDispositionHeaderValue = (headers: any) => {
  const contentDisposition = headers["content-disposition"];

  let filename = "";
  if (contentDisposition && contentDisposition.indexOf("attachment") !== -1) {
    const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
    const matches = filenameRegex.exec(contentDisposition);
    if (matches != null && matches[1]) {
      filename = matches[1].replace(/['"]/g, "");
    }
  }

  return filename;
};
